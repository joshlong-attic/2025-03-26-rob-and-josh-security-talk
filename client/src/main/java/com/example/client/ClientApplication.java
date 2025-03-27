package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RouterFunctions.route;


@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }


}

@Configuration
class GatewayConfiguration {

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route()
                .filter(TokenRelayFilterFunctions.tokenRelay())
                .GET("/**", http("http://localhost:8081"))
                .build();
    }
}


///
///  todo show how to autoconfigure the interceptor
//@Controller
@ResponseBody
class ClientController {

    private final RestClient restClient;

    ClientController(RestClient.Builder restClient) {
        this.restClient = restClient.build();
    }

    @GetMapping("/")
    ResponseEntity<?> index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return this.restClient
                .get()
                .uri("http://localhost:8081")
                .headers(http -> http
                        .setBearerAuth(authorizedClient.getAccessToken().getTokenValue())
                )
                .retrieve()
                .toEntity(String.class);
    }
}
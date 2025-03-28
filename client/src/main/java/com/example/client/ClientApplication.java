package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;
import static org.springframework.web.servlet.function.RouterFunctions.route;


@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	public RestClient restClient(RestClient.Builder builder, OAuth2AuthorizedClientManager authorizedClientManager) {
		OAuth2ClientHttpRequestInterceptor requestInterceptor =
				new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
		requestInterceptor.setClientRegistrationIdResolver(request -> "spring");

		return builder.requestInterceptor(requestInterceptor).build();
	}

}

@Configuration
class RouterConfiguration {

	@Bean
	RouterFunction<ServerResponse> routerFunction(RestClient rest) {
		return route()
			.GET("/**", request -> {
				String body = rest.get()
					.uri("http://service.local.gd:8081")
					.retrieve()
					.body(String.class);
				return ServerResponse.ok().body(body);
			})
			.build();
	}
}
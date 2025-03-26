package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Map;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

  /*  @Bean
    UserDetailsService userDetailsService(PasswordEncoder pw) {
        var userProto = User.builder()
                .authorities("ROLE")
                .passwordEncoder(pw::encode)
                .password("pw");
        var users = Set.of(
            userProto.username("rob").build(),
            userProto.username("josh").build()
        );
        return new InMemoryUserDetailsManager(users);
    }
*/
}

@Controller
@ResponseBody
class GreetingsController {

    @GetMapping("/hi")
    Map<String, String> greetings(Principal principal) {
        return Map.of("greeting", "Hello " + principal.getName() + "!");
    }
}

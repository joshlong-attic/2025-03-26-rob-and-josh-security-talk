package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
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
    SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(ae -> ae.anyRequest().authenticated())
                .webAuthn(wa -> wa
                        .rpId("localhost")
                        .rpName("Bootiful Apps")
                        .allowedOrigins("http://localhost:8080")
                )
                .oneTimeTokenLogin(ott -> ott
                        .tokenGenerationSuccessHandler((request, response, oneTimeToken) -> {
                            var value = oneTimeToken.getTokenValue();
                            System.out.println("go to http://localhost:8080/login/ott?token=" + value);
                            response.getWriter().print("you've got console mail!");
                            response.setContentType(MediaType.TEXT_PLAIN.toString());
                        }))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsPasswordService userDetailsPasswordService(JdbcUserDetailsManager userDetailsManager) {
        return (user, newPassword) -> {
            var newUser = User.withUserDetails(user).password(newPassword).build();
            userDetailsManager.updateUser(newUser);
            return newUser;
        };
    }

    @Bean
    JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
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

    @GetMapping("/")
    Map<String, String> greetings(Principal principal) {
        return Map.of("greeting", "Hello " + principal.getName() + "!");
    }
}

package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@ResponseBody
@SpringBootApplication
@EnableMethodSecurity
public class ServiceApplication {

    @Bean
    AnnotationTemplateExpressionDefaults expressionDefaults() {
        return new AnnotationTemplateExpressionDefaults();
    }

    @GetMapping("/")
    Map<String, Object> index(Principal principal) {
        return Map.of("message", "Hello " + principal.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}

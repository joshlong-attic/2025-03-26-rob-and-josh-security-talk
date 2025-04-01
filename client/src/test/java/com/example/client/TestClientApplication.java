package com.example.client;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import testjars.service.ServiceApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.experimental.boot.server.exec.CommonsExecWebServer;
import org.springframework.experimental.boot.server.exec.CommonsExecWebServerFactoryBean;
import org.springframework.experimental.boot.test.context.DynamicPortUrl;
import org.springframework.experimental.boot.test.context.OAuth2ClientProviderIssuerUri;

import static org.springframework.experimental.boot.server.exec.MavenClasspathEntry.springBootStarter;

@TestConfiguration(proxyBeanMethods = false)
public class TestClientApplication {
	public static void main(String[] args) {
		SpringApplication.from(ClientApplication::main)
			.with(TestClientApplication.class)
			.run(args);
	}

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
	}

	@Bean
	@OAuth2ClientProviderIssuerUri(host = "localhost")
	CommonsExecWebServerFactoryBean authzServer(PostgreSQLContainer<?> postgresContainer) {
		return CommonsExecWebServerFactoryBean.builder()
			.systemProperties(props -> {
				props.put("spring.datasource.url", postgresContainer.getJdbcUrl());
				props.put("spring.datasource.username", postgresContainer.getUsername());
				props.put("spring.datasource.password", postgresContainer.getPassword());
			})
			.classpath(cp -> cp
				.files("../auth/target/auth-0.0.1-SNAPSHOT.jar")
			);
	}

	@Bean
	@DynamicPortUrl(name = "service.url", host = "service.local.gd")
	CommonsExecWebServerFactoryBean service(CommonsExecWebServer authzServer) {
		String issuerUriProp = "spring.security.oauth2.resourceserver.jwt.issuer-uri";
		int port = authzServer.getPort();
		return CommonsExecWebServerFactoryBean.builder()
				.systemProperties(props -> props
					.put(issuerUriProp, "http://localhost:" + port)
				)
				.classpath(cp -> cp
					.files("../service/target/service-0.0.1-SNAPSHOT.jar")
				);
	}
}

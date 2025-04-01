package com.example.client;

import testjars.service.ServiceApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
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
	@OAuth2ClientProviderIssuerUri(host = "localhost")
	CommonsExecWebServerFactoryBean authzServer() {
		return CommonsExecWebServerFactoryBean.builder()
			.useGenericSpringBootMain()
			.classpath(cp -> cp
				.entries(springBootStarter("oauth2-authorization-server"))
			);
	}

	@Bean
	@DynamicPortUrl(name = "service.url", host = "service.local.gd")
	CommonsExecWebServerFactoryBean service(CommonsExecWebServer authzServer) {
		String issuerUriProp = "spring.security.oauth2.resourceserver.jwt.issuer-uri";
		int port = authzServer.getPort();
		return CommonsExecWebServerFactoryBean.builder()
				.mainClass(ServiceApplication.class.getName())
				.systemProperties(props -> props
					.put(issuerUriProp, "http://localhost:" + port)
				)
				.classpath(cp -> cp
					.entries(springBootStarter("oauth2-resource-server"))
					.entries(springBootStarter("web"))
				);
	}
}

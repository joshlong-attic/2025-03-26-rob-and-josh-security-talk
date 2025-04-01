package com.example.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authorization.AuthorizationProxyFactory;
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BankAccountServiceImplTest {
	AuthorizationProxyFactory factory = AuthorizationAdvisorProxyFactory.withDefaults();

	BankAccountService account = (BankAccountService) factory.proxy(new BankAccountServiceImpl());

	void login(String user) {
		TestingAuthenticationToken auth =
			new TestingAuthenticationToken(user, null, "ROLE_USER");
		SecurityContextHolder.setContext(new SecurityContextImpl(auth));
	}

	@AfterEach
	void cleanup() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void findByIdWhenGranted() {
		login("rob");
		this.account.findById(1);
	}

	@Test
	void findByIdWhenDenied() {
		login("josh");
		assertThatExceptionOfType(AccessDeniedException.class)
			.isThrownBy(() -> this.account.findById(1));
	}

}
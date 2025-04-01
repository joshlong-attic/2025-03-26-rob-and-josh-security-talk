package com.example.service;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class BankAccountServiceImplTest {

	@Autowired
	BankAccountService account;

	@Test
	@WithMockRob
	void findByIdWhenGranted() {
		this.account.findById(1);
	}

	@Test
	@WithMockJosh
	void findByIdWhenDenied() {
		assertThatExceptionOfType(AccessDeniedException.class)
			.isThrownBy(() -> this.account.findById(1));
	}

	@Test
	@WithMockRob
	void findByOwnerWhenGranted() {
		this.account.findByOwner("rob");
	}

	@Test
	@WithMockJosh
	void findByOwnerWhenDenied() {
		assertThatExceptionOfType(AccessDeniedException.class)
				.isThrownBy(() -> this.account.findByOwner("rob"));
	}

	@Test
	@WithMockAccountant
	void findByIdWhenAccountantThenGranted() {
		this.account.findById(1);
	}

	@Test
	@WithMockAccountant
	void findByOwnerWhenAccountantThenGranted() {
		this.account.findByOwner("rob");
	}

	@Test
	@WithMockAccountant
	void findByIdAccountNumberWhenAccountant() {
		BankAccount account = this.account.findById(1);
		assertThatExceptionOfType(AccessDeniedException.class)
			.isThrownBy(() -> account.getAccountNumber());
	}

	@Test
	@WithMockAccountant
	void findByOwnerAccountNumberWhenAccountant() {
		BankAccount account = this.account.findByOwner("rob");
		assertThatExceptionOfType(AccessDeniedException.class)
				.isThrownBy(() -> account.getAccountNumber());
	}
}
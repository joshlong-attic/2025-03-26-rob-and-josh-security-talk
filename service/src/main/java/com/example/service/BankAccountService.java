package com.example.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface BankAccountService {
	@PostReadBankAccount
	BankAccount findById(int id);

	@PostReadBankAccount
	BankAccount findByOwner(String owner);

	@PreAuthorizeOwner(account = "#account")
	default void save(BankAccount account) {}
}

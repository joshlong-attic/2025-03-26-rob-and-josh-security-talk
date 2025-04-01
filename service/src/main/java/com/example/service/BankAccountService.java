package com.example.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface BankAccountService {
	@PostReadBankAccount
	BankAccount findById(int id);

	@PostReadBankAccount
	BankAccount findByOwner(String owner);

	@PreAuthorize("#account?.owner == authentication?.name")
	default void save(BankAccount account) {}
}

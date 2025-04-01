package com.example.service;

public interface BankAccountService {
	@PostReadBankAccount
	BankAccount findById(int id);

	@PostReadBankAccount
	BankAccount findByOwner(String owner);
}

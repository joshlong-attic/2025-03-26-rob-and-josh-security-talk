package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	@Override
	public BankAccount findById(int id) {
		BankAccount account = new BankAccount(id, "rob", "12345", 543.21);

		return account;
	}

	@Override
	public BankAccount findByOwner(String owner) {
		BankAccount account = new BankAccount(1, owner, "12345", 543.21);

		return account;
	}
}

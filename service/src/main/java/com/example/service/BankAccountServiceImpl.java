package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	@Override
	public BankAccount findById(int id) {
		BankAccount account = new BankAccount(id, "rob", "12345", 543.21);

		return account;
	}
}

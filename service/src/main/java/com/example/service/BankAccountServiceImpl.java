package com.example.service;

public class BankAccountServiceImpl implements BankAccountService {
	@Override
	public BankAccount findById(int id) {
		BankAccount account = new BankAccount(id, "rob", "12345", 543.21);

		return account;
	}
}

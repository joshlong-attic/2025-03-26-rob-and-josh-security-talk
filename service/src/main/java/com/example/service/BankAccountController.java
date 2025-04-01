package com.example.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BankAccountController {
	final BankAccountService accounts;

	BankAccountController(BankAccountService accounts) {
		this.accounts = accounts;
	}

	@GetMapping("/accounts/{id}")
	BankAccount findById(@PathVariable int id) {
		return this.accounts.findById(id);
	}
}

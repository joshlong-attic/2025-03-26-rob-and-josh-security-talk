package com.example.service;

import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BankAccountService {
	public BankAccount findById(int id) {
		BankAccount account = new BankAccount(id, "rob", "12345", 543.21);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getName().equals(account.getOwner())) {
			throw new AuthorizationDeniedException("Access denied");
		}
		return account;
	}
}

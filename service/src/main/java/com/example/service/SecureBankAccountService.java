package com.example.service;

import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecureBankAccountService extends BankAccountService {
	@Override
	public BankAccount findById(int id) {
		BankAccount account = super.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getName().equals(account.getOwner())) {
			throw new AuthorizationDeniedException("Access denied");
		}
		return account;
	}
}

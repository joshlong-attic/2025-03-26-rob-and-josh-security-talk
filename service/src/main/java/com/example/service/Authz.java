package com.example.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Service
public class Authz {
	public boolean check(Authentication authentication, BankAccount account) {
		if (authentication.getName().equals(account.getOwner())) {
			return true;
		}
		if (authentication.getName().equals("accountant")) {
			return true;
		}
		return hasRole("ACCOUNTANT").authorize(() -> authentication, account).isGranted();
	}
}

package com.example.service;

import org.springframework.security.access.prepost.PostAuthorize;

public interface BankAccountService {
	@PostAuthorize("returnObject?.owner == authentication?.name")
	BankAccount findById(int id);
}

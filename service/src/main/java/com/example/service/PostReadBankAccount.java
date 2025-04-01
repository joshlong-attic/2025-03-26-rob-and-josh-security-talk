package com.example.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PostAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PostAuthorize("returnObject?.owner == authentication?.name or hasRole('ACCOUNTANT')")
public @interface PostReadBankAccount {
}

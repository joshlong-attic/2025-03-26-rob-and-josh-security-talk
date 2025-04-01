package com.example.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "accountant", roles = "ACCOUNTANT")
public @interface WithMockAccountant {
}

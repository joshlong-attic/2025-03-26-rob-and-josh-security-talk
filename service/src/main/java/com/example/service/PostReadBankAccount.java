package com.example.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authorization.method.AuthorizeReturnObject;

@Retention(RetentionPolicy.RUNTIME)
@PostAuthorize("@authz.check(authentication, returnObject)")
@AuthorizeReturnObject
public @interface PostReadBankAccount {
}

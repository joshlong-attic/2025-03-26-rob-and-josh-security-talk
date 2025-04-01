package com.example.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("{account}?.owner == authentication?.name")
public @interface PreAuthorizeOwner {
	String account();
}

package com.salapp.bank.userservice.controller;

import com.salapp.bank.userservice.payload.AuthTokenResponse;
import com.salapp.bank.userservice.payload.LoginRequest;

import org.springframework.http.ResponseEntity;

public interface IAuthController {

    ResponseEntity<AuthTokenResponse> authenticateUser(LoginRequest loginRequest);
}

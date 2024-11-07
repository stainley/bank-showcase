package com.salapp.bank.accountservice.controller;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.shared.model.Account;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface IAccountController<R extends Account> {

    <T extends Account> void createAccount(@RequestBody AccountRequest<T> request, HttpServletResponse response) throws IOException;

    /*
    /accounts/{userId} - Get all accounts for a user.
    /accounts/{accountId} - Get a specific account.
    /accounts/savings, /accounts/checking, /accounts/credit - Create different account types.
    /accounts/{accountId}/transactions - Get transactions for an account.
    */
}

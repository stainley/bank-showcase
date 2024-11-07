package com.salapp.bank.accountservice.service;

import com.salapp.bank.shared.model.Account;

import java.util.List;

public interface AccountService<T extends Account> {

    T createAccount(T account);

    T getAccountById(Long id);

    List<T> getAllAccounts();

    void deleteAccount(Long id);
}
package com.salapp.bank.accountservice.service;

import com.salapp.bank.common.model.Account;

import java.util.List;

public interface IAccountService<T extends Account> {

    T createAccount(T account);

    T getAccountById(Long id);

    List<T> getAllAccounts();

    void deleteAccount(Long id);


}

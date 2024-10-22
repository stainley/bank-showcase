package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.accountservice.dto.AccountResponse;
import com.salapp.bank.accountservice.model.Account;

import java.util.List;

public interface IAccountService<T extends Account> {

    T createAccount(T account);

    T getAccountById(Long id);

    List<T> getAllAccounts();

    void deleteAccount(Long id);

    AccountResponse createAccount(AccountRequest account);

    AccountResponse getAccount(String accountId);

    AccountResponse updateAccount(String accountId, AccountRequest account);

    void deleteAccount(String accountId);

    List<AccountResponse> getAllAccountsForUser(String userId);

}

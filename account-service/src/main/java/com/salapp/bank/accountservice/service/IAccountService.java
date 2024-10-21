package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.accountservice.dto.AccountResponse;

import java.util.List;

public interface IAccountService {

    AccountResponse createAccount(AccountRequest account);

    AccountResponse getAccount(String accountId);

    AccountResponse updateAccount(String accountId, AccountRequest account);

    void deleteAccount(String accountId);

    List<AccountResponse> getAllAccountsForUser(String userId);

}

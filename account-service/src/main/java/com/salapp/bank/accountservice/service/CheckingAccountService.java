package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.model.CheckingAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckingAccountService implements AccountService<CheckingAccount> {

    @Override
    public CheckingAccount createAccount(CheckingAccount account) {
        return null;
    }

    @Override
    public CheckingAccount getAccountById(Long id) {
        return null;
    }

    @Override
    public List<CheckingAccount> getAllAccounts() {
        return List.of();
    }

    @Override
    public void deleteAccount(Long id) {

    }
}

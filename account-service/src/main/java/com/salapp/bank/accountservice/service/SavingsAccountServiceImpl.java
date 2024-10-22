package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.accountservice.dto.AccountResponse;
import com.salapp.bank.accountservice.model.SavingAccount;
import com.salapp.bank.accountservice.repository.SavingsAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SavingsAccountServiceImpl implements IAccountService<SavingAccount> {

    private final SavingsAccountRepository savingsAccountRepository;

    @Override
    public SavingAccount createAccount(SavingAccount account) {
        return savingsAccountRepository.save(account);
    }

    @Override
    public SavingAccount getAccountById(Long id) {
        return savingsAccountRepository.findById(id).orElse(null);
    }

    @Override
    public List<SavingAccount> getAllAccounts() {
        return savingsAccountRepository.findAll();
    }

    @Override
    public void deleteAccount(Long id) {
        savingsAccountRepository.deleteById(id);
    }

    @Override
    public AccountResponse createAccount(AccountRequest account) {
        return null;
    }

    @Override
    public AccountResponse getAccount(String accountId) {
        return null;
    }

    @Override
    public AccountResponse updateAccount(String accountId, AccountRequest account) {
        return null;
    }

    @Override
    public void deleteAccount(String accountId) {

    }

    @Override
    public List<AccountResponse> getAllAccountsForUser(String userId) {
        return List.of();
    }
}

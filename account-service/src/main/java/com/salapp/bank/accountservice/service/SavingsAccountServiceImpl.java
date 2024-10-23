package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.model.SavingAccount;
import com.salapp.bank.accountservice.repository.SavingsAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsAccountServiceImpl implements IAccountService<SavingAccount> {

    private final SavingsAccountRepository savingsAccountRepository;

    public SavingsAccountServiceImpl(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }

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
        //return savingsAccountRepository.findAll();
        return null;
    }

    @Override
    public void deleteAccount(Long id) {
        savingsAccountRepository.deleteById(id);
    }

}

package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.model.SavingAccount;
import com.salapp.bank.accountservice.repository.SavingsAccountRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SavingsAccountServiceImpl implements IAccountService<SavingAccount> {

    private final SavingsAccountRepository savingsAccountRepository;

    public SavingsAccountServiceImpl(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }


    @Transactional
    @CacheEvict(value = "accounts", key = "#account.accountId")
    @Secured("{ROLE_USER}")
    @Override
    public SavingAccount createAccount(SavingAccount account) {
        return savingsAccountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "accounts", key = "#id")
    @Secured({"{ROLE_USER", "ROLE_ADMIN"})
    @Override
    public SavingAccount getAccountById(Long id) {
        return savingsAccountRepository.findById(id).orElse(null);
    }

    @Override
    public List<SavingAccount> getAllAccounts() {

        return List.of();
    }

    @Secured("{ROLE_ADMIN}")
    @Override
    public void deleteAccount(Long id) {
        savingsAccountRepository.deleteById(id);
    }

}

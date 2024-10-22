package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.accountservice.dto.AccountResponse;
import com.salapp.bank.accountservice.exception.AccountNotFoundException;
import com.salapp.bank.accountservice.model.Account;
import com.salapp.bank.accountservice.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    @CacheEvict(value = "accounts", key = "#accountRequest.id")
    @Secured("{ROLE_USER}")
    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account account = accountRequest.mapToAccount();
        Account accountCreated = accountRepository.save(account);

        return null;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "accounts", key = "#accountId")
    @Secured({"{ROLE_USER", "ROLE_ADMIN"})
    public AccountResponse getAccount(long accountId) throws AccountNotFoundException {

        Account accountFound = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("" + accountId));

        return null;

    }

    public AccountResponse updateAccount(long accountId, AccountRequest account) {

        return null;
    }

    @Secured("{ROLE_ADMIN}")
    public void deleteAccount(long accountId) {

    }
}

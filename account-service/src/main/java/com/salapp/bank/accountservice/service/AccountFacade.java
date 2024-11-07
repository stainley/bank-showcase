package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.accountservice.dto.AccountResponse;
import com.salapp.bank.shared.model.Account;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class AccountFacade {

    private final Map<Class<? extends Account>, AccountService<? extends Account>> accountServices;

    public AccountFacade(Map<Class<? extends Account>, AccountService<? extends Account>> accountServices) {
        this.accountServices = accountServices;
    }

    public <T extends Account> AccountResponse<T> createAccount(AccountRequest<T> request) {
        T account = request.account();
        AccountService<T> service = getAccountService((Class<T>) account.getClass())
                .orElseThrow(() -> new IllegalArgumentException("No service found for account type: " + account.getClass().getName()));

        T savedAccount = service.createAccount(account);

        return AccountResponse.<T>builder()
                .account(savedAccount)
                .build();
    }


    // Helper method to retrieve the appropriate AccountService based on account type
    @SuppressWarnings("unchecked")
    private <T extends Account> Optional<AccountService<T>> getAccountService(Class<T> accountType) {
        return Optional.ofNullable((AccountService<T>) accountServices.get(accountType));
    }
}

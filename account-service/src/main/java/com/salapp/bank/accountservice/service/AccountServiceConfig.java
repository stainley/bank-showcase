package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.model.CheckingAccount;
import com.salapp.bank.accountservice.model.SavingAccount;
import com.salapp.bank.shared.model.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AccountServiceConfig {

    private final SavingsAccountService savingsAccountService;
    private final CheckingAccountService checkingAccountService;

    public AccountServiceConfig(SavingsAccountService savingsAccountService, CheckingAccountService checkingAccountService) {
        this.savingsAccountService = savingsAccountService;
        this.checkingAccountService = checkingAccountService;
    }

    @Bean
    public Map<Class<? extends Account>, AccountService<? extends Account>> accountServices() {
        Map<Class<? extends Account>, AccountService<? extends Account>> accountServiceMap = new HashMap<>();
        accountServiceMap.put(SavingAccount.class, savingsAccountService);
        accountServiceMap.put(CheckingAccount.class, checkingAccountService);
        return accountServiceMap;
    }
}

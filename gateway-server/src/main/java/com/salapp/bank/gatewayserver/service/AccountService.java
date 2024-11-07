package com.salapp.bank.gatewayserver.service;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final CircuitBreakerFactory circuitBreakerFactory;

    public AccountService(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public String getAccountData(String accountId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("accountServiceCircuitBreaker");

        return circuitBreaker.run(() -> callAccountService(accountId), throwable -> handleAccountServiceFallback(accountId, throwable));
    }

    private String callAccountService(String accountId) {
        // Logic to call the account service (via REST template or WebClient)
        return "Account data";
    }

    private String handleAccountServiceFallback(String accountId, Throwable throwable) {
        // Custom fallback logic when the circuit is open or a failure occurs
        return "Fallback account data";
    }
}

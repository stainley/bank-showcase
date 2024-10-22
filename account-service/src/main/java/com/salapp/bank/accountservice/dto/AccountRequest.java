package com.salapp.bank.accountservice.dto;

import com.salapp.bank.accountservice.model.Account;

import java.math.BigDecimal;

public record AccountRequest(Long id, Long userId, String accountType, BigDecimal balance) {

    public Account mapToAccount() {
        return null;
    }
}

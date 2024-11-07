package com.salapp.bank.accountservice.dto;


import com.salapp.bank.shared.model.Account;

import java.math.BigDecimal;

public record AccountRequest<T extends Account>(Long id, Long userId, String accountType, BigDecimal balance,
                                                T account) {

    public Object mapToAccount() {
        return null;
    }

    public Account getAccount() {
        return null;
    }
}

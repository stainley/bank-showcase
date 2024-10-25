package com.salapp.bank.accountservice.dto;


import java.math.BigDecimal;

public record AccountRequest(Long id, Long userId, String accountType, BigDecimal balance) {

    public Object mapToAccount() {
        return null;
    }
}

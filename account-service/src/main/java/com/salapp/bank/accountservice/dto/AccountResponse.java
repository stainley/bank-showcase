package com.salapp.bank.accountservice.dto;

import com.salapp.bank.accountservice.model.Account;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse(Long id, Long userId, String accountType, BigDecimal balance) {


}

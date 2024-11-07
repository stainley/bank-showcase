package com.salapp.bank.accountservice.dto;

import com.salapp.bank.shared.model.Account;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse<T extends Account>(Long id, Long userId, String accountType, BigDecimal balance,
                                                 T account) {


}

package com.salapp.bank.common.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public abstract class Account {

    private Long accountId;

    @NotNull
    private String userId;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private LocalDateTime createdAt;

    protected Account(Long accountId, String userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public abstract void deposit(BigDecimal amount);

    public abstract void withdraw(BigDecimal amount);

}

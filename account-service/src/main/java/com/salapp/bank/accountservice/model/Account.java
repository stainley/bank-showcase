package com.salapp.bank.accountservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accounts")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotNull
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime createdAt;

    protected Account(Long accountId, String userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public abstract void deposit(BigDecimal amount);

    public abstract void withdraw(BigDecimal amount);

}

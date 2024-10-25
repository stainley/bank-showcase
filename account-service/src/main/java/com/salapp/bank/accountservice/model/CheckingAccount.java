package com.salapp.bank.accountservice.model;


import com.salapp.bank.accountservice.exception.InsufficientBalanceException;
import com.salapp.bank.shared.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "checking_accounts")
public class CheckingAccount extends Account {
    @Id
    private Long id;

    @Column(name = "overdraft_protection", nullable = false)
    private boolean overdraftProtection;

    public CheckingAccount(Long accountId, String userId, BigDecimal balance, boolean overdraftProtection) {
        super(accountId, userId, balance);
        this.overdraftProtection = overdraftProtection;
    }

    //@Override
    public void deposit(BigDecimal amount) {
        setBalance(getBalance().add(amount));
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (getBalance().compareTo(amount) >= 0 || overdraftProtection) {
            setBalance(getBalance().subtract(amount));
        } else {
            throw new InsufficientBalanceException("Overdraft protection not available");
        }
    }
}

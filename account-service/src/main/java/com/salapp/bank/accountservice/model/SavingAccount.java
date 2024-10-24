package com.salapp.bank.accountservice.model;

import com.salapp.bank.accountservice.exception.InsufficientBalanceException;

import com.salapp.bank.common.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "savings_accounts")
public class SavingAccount extends Account {

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "minimum_balance", nullable = false)
    private BigDecimal minimumBalance;

    @Min(value = 0)
    @Column(name = "widthdrawal_limit", nullable = false)
    private int withdrawalLimit;

    public SavingAccount(Long accountId, String userId, BigDecimal balance, BigDecimal interestRate, BigDecimal minimumBalance, int withdrawalLimit) {
        super(accountId, userId, balance);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.withdrawalLimit = withdrawalLimit;
    }

    @Override
    public void deposit(BigDecimal amount) {
        setBalance(getBalance().add(amount));
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (getBalance().compareTo(amount) >= 0 && getBalance().compareTo(minimumBalance) >= 0) {
            setBalance(getBalance().subtract(amount));
        } else {
            throw new InsufficientBalanceException("Insufficient balance or minimum balance required.");
        }
    }

    public void accumulateInterest() {
        setBalance(getBalance().add(interestRate).multiply(interestRate));
    }
}

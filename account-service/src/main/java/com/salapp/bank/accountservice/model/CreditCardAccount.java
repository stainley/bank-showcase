package com.salapp.bank.accountservice.model;

import com.salapp.bank.accountservice.exception.InsufficientBalanceException;
import com.salapp.bank.shared.model.Account;
import com.salapp.bank.shared.model.JpaAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "credit_card_accounts")
public class CreditCardAccount extends JpaAccount {

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "credit_limit", nullable = false)
    private BigDecimal creditLimit;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "insterest_rage", nullable = false)
    private BigDecimal interestRate;

    @Column(nullable = false)
    private LocalDateTime billingCycle;

    public CreditCardAccount(Long accountId, String userId, BigDecimal balance, BigDecimal creditLimit, BigDecimal interestRate) {
        super(accountId, userId, balance);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(BigDecimal amount) {
        setBalance(getBalance().add(amount));
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (getBalance().add(creditLimit).compareTo(amount) >= 0) {
            setBalance(getBalance().subtract(amount));
        } else {
            throw new InsufficientBalanceException("Credit limit exceeded.");
        }
    }

    public void applyInterest() {
        setBalance(getBalance().add(getBalance().multiply(interestRate)));
    }
}

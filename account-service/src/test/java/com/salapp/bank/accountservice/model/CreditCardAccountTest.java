package com.salapp.bank.accountservice.model;

import com.salapp.bank.accountservice.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardAccountTest {
    private CreditCardAccount creditCardAccount;

    @BeforeEach
    void setUp() {
        // Set up a CreditCardAccount with initial values
        creditCardAccount = new CreditCardAccount(1L, "user1", BigDecimal.valueOf(500.00),
                BigDecimal.valueOf(2000.00), BigDecimal.valueOf(0.1));
        creditCardAccount.setBillingCycle(LocalDateTime.now());
    }

    @Test
    void testDepositIncreasesBalance() {
        BigDecimal initialBalance = creditCardAccount.getBalance();
        BigDecimal depositAmount = BigDecimal.valueOf(100.00);

        creditCardAccount.deposit(depositAmount);

        assertEquals(initialBalance.add(depositAmount), creditCardAccount.getBalance());
    }

    @Test
    void testWithdrawWithinBalanceAndCreditLimit() {
        BigDecimal initialBalance = creditCardAccount.getBalance();
        BigDecimal withdrawAmount = BigDecimal.valueOf(400.00);

        creditCardAccount.withdraw(withdrawAmount);

        assertEquals(initialBalance.subtract(withdrawAmount), creditCardAccount.getBalance());
    }

    @Test
    void testWithdrawExceedingBalanceAndCreditLimit() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(3000.00);

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            creditCardAccount.withdraw(withdrawAmount);
        });

        assertEquals("Credit limit exceeded.", exception.getMessage());
        assertEquals(BigDecimal.valueOf(500.00), creditCardAccount.getBalance()); // Balance should remain unchanged
    }

    @Test
    void testApplyInterestIncreasesBalance() {
        BigDecimal initialBalance = creditCardAccount.getBalance();
        creditCardAccount.applyInterest();

        assertEquals(initialBalance.add(initialBalance.multiply(creditCardAccount.getInterestRate())), creditCardAccount.getBalance());
    }
}
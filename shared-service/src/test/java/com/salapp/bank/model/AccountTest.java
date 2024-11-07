package com.salapp.bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountTest {

    private TestJpaAccount jpaAccount;
    private TestMongoAccount mongoAccount;

    @BeforeEach
    void setup() {
        jpaAccount = new TestJpaAccount();
        mongoAccount = new TestMongoAccount();

        jpaAccount.setBalance(BigDecimal.valueOf(1000.00));
        mongoAccount.setBalance(BigDecimal.valueOf(1000.00));
    }

    @Test
    void testDepositJpaAccount() {
        BigDecimal depositAmount = BigDecimal.valueOf(200.00);
        jpaAccount.deposit(depositAmount);

        Assertions.assertEquals(BigDecimal.valueOf(1200.00), jpaAccount.getBalance(), "Balance should increase by deposit amount.");
    }

    @Test
    void testDepositMongoAccount() {
        BigDecimal depositAmount = BigDecimal.valueOf(200.00);
        mongoAccount.deposit(depositAmount);

        Assertions.assertEquals(BigDecimal.valueOf(1200.00), mongoAccount.getBalance(), "Balance should increase by deposit amount.");
    }

    @Test
    void testWithdrawJpaAccount() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(300.00);
        jpaAccount.withdraw(withdrawAmount);

        Assertions.assertEquals(BigDecimal.valueOf(700.00), jpaAccount.getBalance(), "Balance should increase by withdraw amount.");
    }

    @Test
    void testWithdrawMongoAccount() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(300.00);
        mongoAccount.withdraw(withdrawAmount);

        Assertions.assertEquals(BigDecimal.valueOf(700.00), mongoAccount.getBalance(), "Balance should increase by withdraw amount.");
    }

    @Test
    void testWithdrawMoreThanBalanceJpaAccount() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(2000.00);
        jpaAccount.withdraw(withdrawAmount);

        Assertions.assertTrue(jpaAccount.getBalance().compareTo(BigDecimal.ZERO) < 0, "Balance should be negative after over-withdrawal.");
    }

    @Test
    void testWithdrawMoreThanBalanceMongoAccount() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(2000.00);
        mongoAccount.withdraw(withdrawAmount);

        Assertions.assertTrue(mongoAccount.getBalance().compareTo(BigDecimal.ZERO) < 0, "Balance should be negative after over-withdrawal.");
    }
}

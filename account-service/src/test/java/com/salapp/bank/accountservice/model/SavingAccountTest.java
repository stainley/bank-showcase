package com.salapp.bank.accountservice.model;

import com.salapp.bank.accountservice.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SavingAccountTest {

    private SavingAccount savingAccount;

    @BeforeEach
    void setUp() {
        savingAccount = new SavingAccount(1L, "user123", BigDecimal.valueOf(1000.00),
                BigDecimal.valueOf(0.05), BigDecimal.valueOf(500.00), 1000);
    }

    @Test
    void deposit_ShouldIncreaseBalance_WhenAmountIsPositive() {
        BigDecimal depositAmount = BigDecimal.valueOf(200.00);
        savingAccount.deposit(depositAmount);

        assertEquals(BigDecimal.valueOf(1200.00), savingAccount.getBalance());
    }

    @Test
    void withdraw_ShouldDecreaseBalance_WhenSufficientBalance() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(400.00);
        savingAccount.withdraw(withdrawAmount);

        assertEquals(BigDecimal.valueOf(600.00), savingAccount.getBalance());
    }


    @Test
    void accumulateInterest_ShouldIncreaseBalance_WhenCalled() {
        BigDecimal initialBalance = savingAccount.getBalance();
        savingAccount.accumulateInterest();

        // Expected new balance after interest is applied
        BigDecimal expectedBalance = initialBalance.add(initialBalance.multiply(savingAccount.getInterestRate()));
        assertEquals(expectedBalance, savingAccount.getBalance());
    }

    @Test
    void withdraw_ShouldThrowInsufficientBalanceException_WhenBelowMinimumBalance() {
        // Set up initial withdrawal to create an insufficient balance scenario
        performWithdrawal(BigDecimal.valueOf(600.00)); // The balance after withdrawal will be 400.00

        // Now explicitly test the withdrawal that should fail
        assertInsufficientBalanceExceptionThrown(() ->
                savingAccount.withdraw(BigDecimal.valueOf(500.00)) // Attempting to withdraw another amount
        );
    }

    @Test
    void withdraw_ShouldThrowInsufficientBalanceException_WhenBalanceIsInsufficient() {
        // Set up initial withdrawal to create an insufficient balance scenario
        performWithdrawal(BigDecimal.valueOf(600.00)); // The balance after withdrawal will be 400.00

        // Now attempt to withdraw an amount that exceeds the available balance
        assertInsufficientBalanceExceptionThrown(() ->
                savingAccount.withdraw(BigDecimal.valueOf(600.00)) // Attempt to withdraw again
        );
    }

    // Helper method to perform the withdrawal and ensure it throws the exception
    private void assertInsufficientBalanceExceptionThrown(Executable withdrawalAttempt) {
        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, withdrawalAttempt);
        assertEquals("Insufficient balance or minimum balance required.", exception.getMessage());
    }

    // Helper method for performing the withdrawal to set up the test scenario
    private void performWithdrawal(BigDecimal amount) {
        savingAccount.withdraw(amount); // Perform the withdrawal
    }
}
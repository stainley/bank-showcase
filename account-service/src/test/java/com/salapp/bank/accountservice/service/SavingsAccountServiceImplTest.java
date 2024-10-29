package com.salapp.bank.accountservice.service;

import com.salapp.bank.accountservice.model.SavingAccount;
import com.salapp.bank.accountservice.repository.SavingsAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SavingsAccountServiceImplTest {

    @Mock
    private SavingsAccountRepository savingsAccountRepository;

    @InjectMocks
    private SavingsAccountServiceImpl savingsAccountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        // Arrange
        SavingAccount account = new SavingAccount();
        account.setAccountId(1L);
        account.setBalance(BigDecimal.valueOf(1000.0));
        when(savingsAccountRepository.save(any(SavingAccount.class))).thenReturn(account);

        // Act
        SavingAccount createdAccount = savingsAccountService.createAccount(account);

        // Assert
        assertNotNull(createdAccount);
        assertEquals(1L, createdAccount.getAccountId());
        verify(savingsAccountRepository, times(1)).save(account);
    }

    @Test
    void testGetAccountById_AccountExists() {
        // Arrange
        SavingAccount account = new SavingAccount();
        account.setAccountId(1L);
        account.setBalance(BigDecimal.valueOf(1000.0));
        when(savingsAccountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Act
        SavingAccount foundAccount = savingsAccountService.getAccountById(1L);

        // Assert
        assertNotNull(foundAccount);
        assertEquals(1L, foundAccount.getAccountId());
        verify(savingsAccountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAccountById_AccountNotFound() {
        // Arrange
        when(savingsAccountRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        SavingAccount foundAccount = savingsAccountService.getAccountById(1L);

        // Assert
        assertNull(foundAccount);
        verify(savingsAccountRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteAccount() {
        // Arrange
        Long accountId = 1L;

        // Act
        savingsAccountService.deleteAccount(accountId);

        // Assert
        verify(savingsAccountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void testGetAllAccounts() {
        // Arrange: This method currently returns an empty list, you can modify it later.
        // Act
        List<SavingAccount> accounts = savingsAccountService.getAllAccounts();

        // Assert
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

}
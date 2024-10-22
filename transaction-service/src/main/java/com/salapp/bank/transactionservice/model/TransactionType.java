package com.salapp.bank.transactionservice.model;

/**
 * DEPOSIT/ WITHDRAWAL: Basic transactions for adding/removing funds.
 * TRANSFER: Between accounts.
 * PURCHASE/ PAYMENT: Purchases or bill payments.
 * REFUND/ REVERSAL: Corrections or refunds.
 * ATM_WITHDRAWAL/ ONLINE_PAYMENT: More specific use cases.
 * INTEREST_CREDIT: For savings accounts to handle interest accumulation.
 * FEE/ LOAN_REPAYMENT: Additional bank-specific transaction types.
 * CASH_BACK: Credit/Debit card cash-back rewards.
 */
public enum TransactionType {
    DEPOSIT,                // Depositing money into an account
    WITHDRAWAL,             // Withdrawing money from an account
    TRANSFER,               // Transferring funds between accounts
    PURCHASE,               // Purchase using a debit/credit card
    PAYMENT,                // Payment of a bill or invoice
    REFUND,                 // Refund of a transaction
    ATM_WITHDRAWAL,          // Cash withdrawal from an ATM
    ONLINE_PAYMENT,         // Payment made online
    POS_PURCHASE,           // Point of Sale purchase (physical store)
    INTEREST_CREDIT,        // Interest credited to savings account
    LOAN_REPAYMENT,         // Repayment of a loan
    FEE,                   // Transaction fee (maintenance fee, overdraft fee, etc.)
    DIRECT_DEPOSIT,         // Direct deposit (for example, salary, benefits)
    CASH_BACK,              // Cash back on a credit/debit card purchase
    REVERSAL                // Reversal of a previous transaction (correction)
}

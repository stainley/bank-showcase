package com.salapp.bank.transactionservice.event;

import lombok.Getter;

@Getter
public class TransactionCreatedEvent {

    private final String transactionId;
    private final String accountId;
    private final double amount;

    public TransactionCreatedEvent(String transactionId, String accountId, double amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
    }
}

package com.salapp.bank.transactionservice.cqrs;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class CreateTransactionCommand {

    //@TargetAggregateIdentifier
    private final String transactionId;
    private final double accountId;
    private final double amount;

    public CreateTransactionCommand(String transactionId, double accountId, double amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
    }

}

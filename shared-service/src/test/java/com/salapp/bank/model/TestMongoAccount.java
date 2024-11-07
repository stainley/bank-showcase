package com.salapp.bank.model;

import com.salapp.bank.shared.model.MongoAccount;

import java.math.BigDecimal;

public class TestMongoAccount extends MongoAccount {

    @Override
    public void deposit(BigDecimal amount) {
        this.setBalance(this.getBalance().add(amount));
    }

    @Override
    public void withdraw(BigDecimal amount) {
        this.setBalance(this.getBalance().subtract(amount));
    }
}

package com.salapp.bank.shared.model;

import java.math.BigDecimal;

public interface Account {

    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);

}

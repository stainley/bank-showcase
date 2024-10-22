package com.salapp.bank.accountservice.exception;

public class CreditLimitExceededException extends RuntimeException {

    public CreditLimitExceededException(String message) {
        super(message);
    }
}

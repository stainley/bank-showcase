package com.salapp.bank.gatewayserver.exception;

public class UnauthorizedException extends Exception {

    public UnauthorizedException(String invalidJwtToken) {
        super(invalidJwtToken);
    }
}

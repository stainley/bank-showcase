package com.salapp.bank.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsufficientPrivilegeException extends RuntimeException {

    public InsufficientPrivilegeException(String message) {
        super(message);
    }
}

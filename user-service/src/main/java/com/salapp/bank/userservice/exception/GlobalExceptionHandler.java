package com.salapp.bank.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientPrivilegeException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientPrivilege(InsufficientPrivilegeException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.name())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .details(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExceptionUserNotFoundException(UserNotFoundException userNotFoundException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.name())
                .message("User not found")
                .timestamp(LocalDateTime.now())
                .details(userNotFoundException.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}

package com.salapp.bank.gatewayserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/account")
    public ResponseEntity<String> accountServiceFallback() {
        return new ResponseEntity<>("Account service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/fallback/transaction")
    public ResponseEntity<String> transactionServiceFallback() {
        return new ResponseEntity<>("Transaction service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}

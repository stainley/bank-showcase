package com.salapp.bank.accountservice.controller;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.accountservice.dto.AccountResponse;
import com.salapp.bank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;


    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest account) {
        AccountResponse createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable long accountId) {
        AccountResponse accountResponse = accountService.getAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }

    @PutMapping("/accountId")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable long accountId, @RequestBody AccountRequest account) {
        AccountResponse accountResponse = accountService.updateAccount(accountId, account);
        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }


    @GetMapping("/test")
    public ResponseEntity<String> testAccount(@RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok("Hello World with the username: " + username);
    }


}

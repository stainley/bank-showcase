package com.salapp.bank.accountservice.controller;

import com.salapp.bank.accountservice.dto.AccountRequest;
import com.salapp.bank.accountservice.dto.AccountResponse;
import com.salapp.bank.accountservice.service.AccountFacade;
import com.salapp.bank.shared.model.Account;
import com.salapp.bank.shared.model.JpaAccount;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController { //implements IAccountController<Account> {

    /*private final AccountFacade accountFacade;


    @PostMapping
    @Override
    public <T extends Account> void createAccount(@Valid @RequestBody AccountRequest<T> request, HttpServletResponse response) throws IOException {

        AccountResponse<T> accountCreated = accountFacade.createAccount(request);
        // Assuming `getAccountNumber()` returns the unique account number for redirection
        Long accountNumber = ((JpaAccount) accountCreated.account()).getAccountId();

        response.sendRedirect("/api/${api.version}/accounts/" + accountNumber);
    }*/


    @GetMapping("/test")
    public ResponseEntity<String> testAccount() {//(@RequestHeader("X-Csrf-Token") String crsf) {

        return ResponseEntity.ok("Hello Account Service with the username: ");
    }

}

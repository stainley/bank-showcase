package com.salapp.bank.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    @GetMapping("/test")
    public ResponseEntity<String> testAccount(@RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok("Hello World with the username: " + username);
    }


}

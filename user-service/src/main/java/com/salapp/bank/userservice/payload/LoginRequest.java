package com.salapp.bank.userservice.payload;

import lombok.Builder;

@Builder
public record LoginRequest(String email, String password) {

}

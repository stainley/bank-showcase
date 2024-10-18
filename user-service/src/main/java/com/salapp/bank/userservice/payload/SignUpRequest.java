package com.salapp.bank.userservice.payload;

import lombok.Builder;

@Builder
public record SignUpRequest(String email, String password, String role) {

}

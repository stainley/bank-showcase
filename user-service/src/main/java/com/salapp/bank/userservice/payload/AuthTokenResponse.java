package com.salapp.bank.userservice.payload;

import lombok.Builder;

@Builder
public record AuthTokenResponse(String token, String refreshToken, Long expiresIn, String tokeType, String scope) {

}

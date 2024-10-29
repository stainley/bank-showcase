package com.salapp.bank.userservice.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SignUpRequest(@NotEmpty(message = "Email cannot be empty")
                            String email,
                            @NotEmpty(message = "Password cannot be empty")
                            String password,
                            String role
) {

}

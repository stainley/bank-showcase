package com.salapp.bank.userservice.controller;

import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.payload.AuthTokenResponse;
import com.salapp.bank.userservice.payload.LoginRequest;
import com.salapp.bank.userservice.payload.SignUpRequest;
import com.salapp.bank.userservice.security.CustomUserDetailsService;
import com.salapp.bank.userservice.security.JwtTokenProvider;
import com.salapp.bank.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Validated @RequestBody final SignUpRequest signUpRequest) {
        User newUser = userService.registerNewUser(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully: " + newUser.getEmail());
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<AuthTokenResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.email());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
            String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
            Long expiresIn = jwtTokenProvider.getTokenExpirationTime();
            return ResponseEntity.ok(new AuthTokenResponse(accessToken, refreshToken, expiresIn, "Bearer", "read write"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }
}

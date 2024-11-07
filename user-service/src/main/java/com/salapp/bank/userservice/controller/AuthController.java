package com.salapp.bank.userservice.controller;

import com.salapp.bank.userservice.exception.EmptyFieldException;
import com.salapp.bank.userservice.exception.InvalidCredentialException;
import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.payload.AuthTokenResponse;
import com.salapp.bank.userservice.payload.LoginRequest;
import com.salapp.bank.userservice.payload.SignUpRequest;
import com.salapp.bank.userservice.security.CustomUserDetailsService;
import com.salapp.bank.userservice.security.JwtTokenProvider;
import com.salapp.bank.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    private final CsrfTokenRepository csrfTokenRepository;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody final SignUpRequest signUpRequest) {
        if (signUpRequest.email().isEmpty() || signUpRequest.password().isEmpty()) {
            throw new EmptyFieldException("Fill all required fields");
        }
        User newUser = userService.registerNewUser(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully: " + newUser.getEmail());
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<AuthTokenResponse> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.email());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
            String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
            Long expiresIn = jwtTokenProvider.getTokenExpirationTime();

            CsrfToken csrfToken = csrfTokenRepository.generateToken(null);
            response.addHeader("XSRF-TOKEN", csrfToken.getToken());

            return ResponseEntity.ok(new AuthTokenResponse(accessToken, refreshToken, expiresIn, "Bearer", "read write"));
        } catch (Exception e) {
            throw new InvalidCredentialException(e.getMessage());
        }

    }

    @GetMapping("/token")
    public String getToken() {
        CsrfToken token = csrfTokenRepository.generateToken(null);
        log.info("CSRF Token: {}", token.getToken());
        return token.getToken();
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

}

package com.salapp.bank.userservice.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salapp.bank.userservice.payload.LoginRequest;
import com.salapp.bank.userservice.payload.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String REGISTER_URL = "/api/auth/register";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String USER_ROLE = "USER";

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void testRegisterUser() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("john.doe@example.com", "password123", USER_ROLE);
        registerUser(signUpRequest)
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully: " + signUpRequest.email()));
    }

    @Test
    void testLoginUser() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("jane.doe@example.com", "password123", USER_ROLE);
        registerUser(signUpRequest).andExpect(status().isCreated());

        LoginRequest loginRequest = new LoginRequest("jane.doe@example.com", "password123");
        loginUser(loginRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.expiresIn").exists());
    }

    @Test
    void testLoginNonExistentUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("nonexistent@example.com", "password123");
        loginUser(loginRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    void testUserAlreadyExists() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("duplicated.user@example.com", "password123", USER_ROLE);
        registerUser(signUpRequest).andExpect(status().isCreated());

        // Trying to register the same user again
        registerUser(signUpRequest)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email address already in use"))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.details.reason").value("Duplicate Entry"));
    }

    @Test
    void testInvalidLogin() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("invalid.password@example.com", "password123", USER_ROLE);
        registerUser(signUpRequest).andExpect(status().isCreated());

        LoginRequest invalidLogin = new LoginRequest("invalid.password@example.com", "wrongpassword");
        loginUser(invalidLogin)
                .andExpect(status().isUnauthorized());
    }

    // Helper methods to handle user registration and login

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    private ResultActions registerUser(SignUpRequest request) throws Exception {
        return mockMvc.perform(post(REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
                .with(csrf()));
    }

    private ResultActions loginUser(LoginRequest request) throws Exception {
        return mockMvc.perform(post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
                .with(csrf()));
    }
}
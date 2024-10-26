package com.salapp.bank.userservice.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salapp.bank.userservice.controller.AuthController;
import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.payload.LoginRequest;
import com.salapp.bank.userservice.payload.SignUpRequest;
import com.salapp.bank.userservice.security.CustomUserDetailsService;
import com.salapp.bank.userservice.security.JwtTokenProvider;
import com.salapp.bank.userservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {AuthControllerTest.class, AuthController.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @WithMockUser(roles = "USER")
    @Test
    void testRegisterUser() throws Exception {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest("john.doe@example.com", "password123", "USER");
        when(userService.registerNewUser(signUpRequest)).thenReturn(new User(1L, "john.doe@example.com", "", null));

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpRequest))
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully: " + signUpRequest.email()));

        Assertions.assertTrue(true);
    }

    @Test
    @WithMockUser(roles = "USER")
    void testLoginUser() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("jane.doe@example.com", "password123");
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetails.getUsername()).thenReturn(loginRequest.email());
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()))).thenReturn(authentication);
        when(customUserDetailsService.loadUserByUsername(loginRequest.email())).thenReturn(userDetails);
        when(jwtTokenProvider.generateAccessToken(userDetails)).thenReturn("mockAccessToken");
        when(jwtTokenProvider.generateRefreshToken(userDetails)).thenReturn("mockRefreshToken");

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.accessToken").value("mockAccessToken"))
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.refreshToken").value("mockRefreshToken"));
    }


    private String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}

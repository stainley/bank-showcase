package com.salapp.bank.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        // Arrange
        String username = "testUser";
        String jwt = "mockJwtToken";  // Use a mock token directly

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtTokenProvider.validateToken(jwt)).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromJwtToken(jwt)).thenReturn(username);
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be null");
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertEquals(username, authentication.getName(), "Expected username was not set in authentication");
        assertEquals(userDetails, authentication.getPrincipal(), "Expected UserDetails was not set as principal");
        assertTrue(authentication.isAuthenticated(), "Authentication should be marked as authenticated");

        verify(filterChain).doFilter(request, response);
    }


    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        String jwt = "Bearer invalid.token.here";

        when(request.getHeader("Authorization")).thenReturn(jwt);
        when(jwtTokenProvider.validateToken("invalid.token.here")).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that authentication is not set
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that authentication is not set
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_ExceptionHandling() throws ServletException, IOException {
        String jwt = "Bearer valid.token.here";

        when(request.getHeader("Authorization")).thenReturn(jwt);
        when(jwtTokenProvider.validateToken("valid.token.here")).thenThrow(new RuntimeException("Token validation error"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Ensure the filter chain is still called even when an exception occurs
        verify(filterChain).doFilter(request, response);
    }
}
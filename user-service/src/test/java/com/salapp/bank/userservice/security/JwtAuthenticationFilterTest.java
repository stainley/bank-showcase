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
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

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

    /*@Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        // Arrange
        String username = "testUser";

        // Create a mock UserDetails and set its behavior
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        //when(userDetails.getAuthorities()).thenReturn(List.of(""));

        // Generate a valid JWT token using the JwtTokenProvider
        String jwt = jwtTokenProvider.generateAccessToken(userDetails);

        // Log the JWT token for verification
        System.out.println("Generated JWT Token: {}" + jwt);

        // Mock the request header to return the JWT token
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        // Mock the behavior of the JWT token provider and user details service
        when(jwtTokenProvider.validateToken(jwt)).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromJwtToken(jwt)).thenReturn(username);
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        // Verify the security context holds the correct authentication
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be null");
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // Add more detailed assertions to help diagnose any issues
        assertEquals(username, authentication.getName(), "Expected username was not set in authentication");
        assertEquals(userDetails, authentication.getPrincipal(), "Expected UserDetails was not set as principal");
        assertTrue(authentication.isAuthenticated(), "Authentication should be marked as authenticated");

        // Verify that the filter chain is called
        verify(filterChain).doFilter(request, response);
    }*/


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
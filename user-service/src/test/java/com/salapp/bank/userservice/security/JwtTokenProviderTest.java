package com.salapp.bank.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class JwtTokenProviderTest {
    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserDetails userDetails;

    private final long accessExpiration = 1000 * 60 * 60; // 1 hour
    private final long refreshExpiration = 1000 * 60 * 60 * 24; // 1 day

    @BeforeEach
    void setUp() throws Exception {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);

        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getAuthorities()).thenReturn((Collections.emptyList()));

        // Generate a secure secret key
        // Test secret and expiration values
        // Example secret key
        String jwtSecret = String.valueOf(Keys.secretKeyFor(SignatureAlgorithm.HS256));

        // Set values directly for testing
        // Set values directly using reflection
        setPrivateField(jwtTokenProvider, "jwtSecret", jwtSecret);
        setPrivateField(jwtTokenProvider, "accessExpiration", accessExpiration);
        setPrivateField(jwtTokenProvider, "refreshExpiration", refreshExpiration);
        autoCloseable.close();
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testGenerateAccessToken() {
        String token = jwtTokenProvider.generateAccessToken(userDetails);
        assertNotNull(token, "Access token should not be null");

        // Verify the claims in the generated token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtTokenProvider.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testUser", claims.getSubject(), "Username should match");
        assertInstanceOf(List.class, claims.get("role"), "Roles claim should be a list");
    }

    @Test
    void testGenerateRefreshToken() {
        String token = jwtTokenProvider.generateRefreshToken(userDetails);
        assertNotNull(token, "Refresh token should not be null");

        // Verify the claims in the generated token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtTokenProvider.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testUser", claims.getSubject(), "Username should match");
        assertEquals("refresh", claims.get("type"), "Type claim should be refresh");
    }

    @Test
    void testGetUsernameFromJwtToken() {
        String token = jwtTokenProvider.generateAccessToken(userDetails);
        String username = jwtTokenProvider.getUsernameFromJwtToken(token);
        assertEquals("testUser", username, "Extracted username should match");
    }

    @Test
    void testValidateToken_ValidToken() {
        String token = jwtTokenProvider.generateAccessToken(userDetails);
        assertTrue(jwtTokenProvider.validateToken(token), "Token should be valid");
    }

    @Test
    void testValidateToken_InvalidToken() {
        assertFalse(jwtTokenProvider.validateToken("invalid.token.here"), "Invalid token should return false");
    }

    @Test
    void testGetTokenExpirationTime() {
        assertEquals(accessExpiration / 1000, jwtTokenProvider.getTokenExpirationTime(), "Expiration time should match");
    }

    @Test
    void testGetRefreshTokenExpirationTime() {
        assertEquals(refreshExpiration / 1000, jwtTokenProvider.getRefreshTokenExpirationTime(), "Refresh expiration time should match");
    }

}
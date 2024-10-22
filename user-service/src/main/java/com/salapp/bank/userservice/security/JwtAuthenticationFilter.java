package com.salapp.bank.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableWebSecurity
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private String getJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (jwt != null) {
                validateAndSetAuthentication(jwt, request);
            }
        } catch (Exception e) {
            log.error("Error setting authentication", e);
        }
        filterChain.doFilter(request, response);
    }

    private void validateAndSetAuthentication(String jwt, HttpServletRequest request) {
        if (jwtTokenProvider.validateToken(jwt)) {
            String username = jwtTokenProvider.getUsernameFromJwtToken(jwt);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Authentication set for user {}", username);
        } else {
            log.debug("Invalid JWT token");
        }
    }
}

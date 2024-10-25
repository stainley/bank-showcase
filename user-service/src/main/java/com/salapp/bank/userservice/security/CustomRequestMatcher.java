package com.salapp.bank.userservice.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class CustomRequestMatcher implements RequestMatcher {

    private static final String API_PREFIX = "/api/";

    @Override
    public boolean matches(HttpServletRequest request) {
        // Match for mutating methods (POST, PUT, DELETE, PATCH)
        return ("POST".equals(request.getMethod()) ||
                "PUT".equals(request.getMethod()) ||
                "DELETE".equals(request.getMethod()) ||
                "PATCH".equals(request.getMethod())) &&
                request.getRequestURI().startsWith(API_PREFIX);
    }
}

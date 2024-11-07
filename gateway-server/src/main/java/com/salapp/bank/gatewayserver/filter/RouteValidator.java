package com.salapp.bank.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final Logger log = LoggerFactory.getLogger(RouteValidator.class);

    private RouteValidator() {
    }


    // Define a list of public URLs that don't require authentication
    private static final List<String> openApiEndpoints = List.of(
            "/api/auth/login",
            "/api/auth/token",
            "/api/auth/register",
            "api/auth/logout",
            "/api/auth/refresh",
            "/api/auth/forgot-password",
            "/eureka",
            "/api/accounts/test"
    );

    // Method to check if a route is open (public)
    public final Predicate<ServerHttpRequest> isSecured =
            request -> {
                log.info("Is secured this URL: {}", request.getURI().getPath());

                return openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
            };

}

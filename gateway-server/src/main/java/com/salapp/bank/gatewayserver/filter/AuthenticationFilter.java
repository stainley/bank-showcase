package com.salapp.bank.gatewayserver.filter;

import com.salapp.bank.gatewayserver.exception.UnauthorizedException;
import com.salapp.bank.gatewayserver.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final RouteValidator validator;

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            // Log the incoming request
            log.info("Processing request: {}", request.getURI());

            // Check if the route requires authentication
            if (validator.isSecured.test(request)) {

                // Check if the Authorization header is present
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    log.error("Missing or invalid Authorization header: {}", authHeader);
                    throw new UnauthorizedException("Unauthorized: Missing or Invalid Authorization Header");
                }

                // Extract JWT token from the Authorization header
                String token = authHeader.substring(7);

                try {
                    // Validate the JWT token
                    jwtUtil.validateToken(token);

                    // Extract the username from the JWT and add it to the request headers
                    String username = jwtUtil.extractUsername(token);
                    ServerHttpRequest mutatedRequest = request.mutate()
                            .header("loggedInUser", username)
                            .build();

                    log.info("Token validated successfully. Logged in user: {}", username);

                    // Proceed with the filter chain using the mutated request
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());

                } catch (Exception e) {
                    log.error("Invalid access token", e);
                    throw new UnauthorizedException("Unauthorized: Invalid Access Token");
                }
            }

            // If the route does not require authentication, proceed without modification
            return chain.filter(exchange);
        };
    }


    public static class Config {

        private boolean logRequests;
        private boolean logErrors;

        // Getters and setters for the config properties
        public boolean isLogRequests() {
            return logRequests;
        }

        public void setLogRequests(boolean logRequests) {
            this.logRequests = logRequests;
        }

        public boolean isLogErrors() {
            return logErrors;
        }

        public void setLogErrors(boolean logErrors) {
            this.logErrors = logErrors;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "logRequests=" + logRequests +
                    ", logErrors=" + logErrors +
                    '}';
        }
    }
}

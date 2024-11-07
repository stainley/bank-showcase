package com.salapp.bank.gatewayserver.config;

import com.salapp.bank.gatewayserver.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.time.Duration;


@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route("transaction-service", r -> r.path("/api/transactions/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config()))
                                .circuitBreaker(c -> c.setName("transactionServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/transaction"))
                                .retry(retryConfig -> retryConfig.setRetries(5)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY)
                                        .setBackoff(Duration.ofMillis(500), Duration.ofSeconds(5), 2, false)))
                        .uri("lb://accounts-service"))
                .route("user-service", r -> r.path("/api/auth/**", "/api/users/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config()))
                                .circuitBreaker(c -> c.setName("userServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/authentication"))
                                .retry(retryConfig -> retryConfig.setRetries(5)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY)
                                        .setBackoff(Duration.ofMillis(500), Duration.ofSeconds(5), 2, false)))
                        .uri("lb://user-service"))
                .route("accounts-service", r -> r.path("/api/accounts/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config()))
                                .circuitBreaker(c -> c.setName("accountServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/account"))
                                .retry(retryConfig -> retryConfig.setRetries(5)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY)
                                        .setBackoff(Duration.ofMillis(500), Duration.ofSeconds(5), 2, false)))
                        .uri("lb://accounts-service"))
                .build();
    }


    /*@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/auth/**", "/api/users/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config()))
                                .circuitBreaker(c -> c.setName("userServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/authentication"))
                                .retry(retryConfig -> retryConfig.setRetries(5)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY)))
                        .uri("lb://user-service"))
                *//*.uri("http://localhost:8081"))*//*
                .route("accounts-service", r -> r.path("/api/accounts/test")
                        .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config()))
                                .circuitBreaker(c -> c.setName("accountServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/account"))*//*
                                .retry(retryConfig -> retryConfig.setRetries(5)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY))*//*)
                        .uri("lb://accounts-service"))
                *//*.uri("http://localhost:8083"))*//*
                .build();
    }*/
}

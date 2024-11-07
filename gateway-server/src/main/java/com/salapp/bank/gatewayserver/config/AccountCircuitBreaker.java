package com.salapp.bank.gatewayserver.config;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class AccountCircuitBreaker implements CircuitBreaker {


    @Override
    public <T> T run(Supplier<T> toRun, Function<Throwable, T> fallback) {
        return null;
    }
}

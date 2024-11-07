package com.salapp.bank.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomCORSConfiguration {

    private static final List<String> URLS_ACCEPTED = Arrays.asList("https://example.com", "http://localhost:3000", "http:localhost:4200", "http://localhost:8080");


    @Bean
    public CorsConfiguration corsConfiguration() {

        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(URLS_ACCEPTED);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Accept", "Origin", "Cache-Control", "X-Requested-With", "X-XSRF-TOKEN", "Cookie"));
        config.setExposedHeaders(Arrays.asList("Location", "Authorization", "X-XSRF-TOKEN"));
        config.setMaxAge(3600L); // 1 hour
        return config;
    }
}

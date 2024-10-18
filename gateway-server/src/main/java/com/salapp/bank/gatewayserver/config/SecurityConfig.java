package com.salapp.bank.gatewayserver.config;

import com.salapp.bank.gatewayserver.filter.AuthenticationFilter;
import com.salapp.bank.gatewayserver.filter.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;


//@Configuration
public class SecurityConfig {

    /*@Bean
    public SecurityWebFiltersOrder securityWebFiltersOrder(AuthenticationFilter authenticationFilter, AuthorizationFilter authorizationFilter) {

        return SecurityWebFiltersOrder.AUTHENTICATION;
    }*/
}

package com.salapp.bank.gatewayserver;

import com.salapp.bank.gatewayserver.filter.AuthenticationFilter;
import com.salapp.bank.gatewayserver.filter.AuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class GatewayServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

   /* @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**").uri("lb://user-service"))
                .route("account-service", r -> r.path("/accounts/**").uri("lb://account-service"))
                .route("transaction-service", r -> r.path("/transactions/**").uri("lb://transaction-service"))
                .build();
    }*/

    /*@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/public/**").permitAll()
                        .anyExchange().authenticated())
                .addFilterAt(new AuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(new AuthorizationFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
                .build();
    }*/

}

package com.salapp.bank.gatewayserver.filter;

import com.salapp.bank.gatewayserver.exception.UnauthorizedException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-1) // Ensure this filter has high priority
@Component
public class CustomErrorHandlingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(UnauthorizedException.class, e -> {
                    // Handle UnauthorizedException and return 401 response
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                    String errorMessage = "{\"error\": \"Unauthorized\", \"message\": \"" + e.getMessage() + "\"}";
                    byte[] bytes = errorMessage.getBytes();
                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
                });    }
}

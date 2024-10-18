package com.salapp.bank.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Log the incoming request
            logRequest(exchange);

            // Proceed with the next filter in the chain and log the response after it is processed
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> logResponse(exchange)));
        };
    }

    // Helper method to log request details
    private void logRequest(ServerWebExchange exchange) {
        String requestPath = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();
        String headers = exchange.getRequest().getHeaders().toString();

        logger.info("Incoming Request: Method={}, URI={}, Headers={}", method, requestPath, headers);
    }

    // Helper method to log response details
    private void logResponse(ServerWebExchange exchange) {
        String responseStatus = exchange.getResponse().getStatusCode() != null ? exchange.getResponse().getStatusCode().toString() : "Unknown";
        String headers = exchange.getResponse().getHeaders().toString();

        logger.info("Outgoing Response: Status={}, Headers={}", responseStatus, headers);
    }

    public static class Config {

    }
}

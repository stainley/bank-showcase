package com.salapp.bank.gatewayserver.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class TimeoutGatewayFilterFactory extends AbstractGatewayFilterFactory<TimeoutGatewayFilterFactory.Config> {

    public TimeoutGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).timeout(Duration.ofSeconds(config.getTimeoutDuration()))
                    .onErrorResume(throwable -> handleTimeout(exchange));
        };
    }

    private Mono<Void> handleTimeout(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.GATEWAY_TIMEOUT);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        private long timeoutDuration;

        public long getTimeoutDuration() {
            return timeoutDuration;
        }

        public void setTimeoutDuration(long timeoutDuration) {
            this.timeoutDuration = timeoutDuration;
        }
    }
}

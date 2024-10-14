package com.salapp.bank.gatewayserver.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private static final String SECRET_KEY = "secret";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = extractJwtFromRequest(exchange);

        if (token != null && validateToken(token)) {
            Claims claims = getClaimsFromToken(token);
        }
        return chain.filter(exchange);
    }

    private String extractJwtFromRequest(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("Authorization");
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

}

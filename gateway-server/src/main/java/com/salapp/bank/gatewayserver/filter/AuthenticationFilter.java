package com.salapp.bank.gatewayserver.filter;

import com.salapp.bank.gatewayserver.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Date;

//@Component
public class AuthenticationFilter implements WebFilter {

    //@Autowired
    //private OAuth2AuthorizedGrantTypes authorizedGrantTypes;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            try {
                Jws<Claims> claims = Jwts.parserBuilder()
                        .setSigningKey("secret-key")
                        .build().parseClaimsJws(jwt);

                if (claims.getBody().getExpiration().before(new Date())) {
                    return Mono.error(new Exception("Expired JWT token"));
                }
            } catch (Exception e) {
                return Mono.error(new UnauthorizedException("Invalid JWT token"));
            }
        } else {
            return Mono.error(new UnauthorizedException("Missing token"));
        }

        return chain.filter(exchange);
    }
}

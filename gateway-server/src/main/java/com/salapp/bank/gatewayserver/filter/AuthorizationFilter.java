package com.salapp.bank.gatewayserver.filter;

import com.salapp.bank.gatewayserver.exception.UnauthorizedException;
import com.salapp.bank.gatewayserver.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

//@Component
public class AuthorizationFilter //implements WebFilter
{

    /*private final RouteValidator validator;

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = null;

        if (validator.isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new UnauthorizedException("Unauthorized");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();
            if (authHeader != null || !authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }
            try {
                jwtUtil.validateToken(authHeader);

                request = exchange.getRequest()
                        .mutate()
                        .header("loggedInUser", jwtUtil.extractUsername(authHeader)).build();


            } catch (Exception e) {
                System.out.printf("Invalildate access");
                throw new UnauthorizedException("Unauthorized");
            }
        }

        return chain.filter(exchange.mutate().request(request).build());

    }
     */
}

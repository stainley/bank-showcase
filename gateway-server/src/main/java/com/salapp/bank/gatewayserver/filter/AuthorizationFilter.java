package com.salapp.bank.gatewayserver.filter;

import com.salapp.bank.gatewayserver.exception.UnauthorizedException;
import com.salapp.bank.gatewayserver.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Order(1)
@Component
public class AuthorizationFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);

    private final RouteValidator validator;

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
                return Mono.error(new UnauthorizedException("Unauthorized: Missing or Invalid Authorization Header"));
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();

            if (authHeader != null || !authHeader.startsWith("Bearer ")) {

                authHeader = authHeader.substring(7);
            }
            try {
                jwtUtil.validateToken(authHeader);

                // Extract username or other details to create an Authentication token
                String username = jwtUtil.extractUsername(authHeader);
                Collection<GrantedAuthority> authorities = jwtUtil.extractAuthorities(authHeader);


                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                // Log and set the Authentication in SecurityContext
                log.info("Setting SecurityContext with Authentication: " + authentication);
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));


                /*request = exchange.getRequest()
                        .mutate()
                        .build();*/

            } catch (Exception e) {
                System.out.printf("Invalildate access");
                return Mono.error(new UnauthorizedException("Unauthorized: Missing or Invalid Authorization Header"));
            }
        }

        //return chain.filter(exchange.mutate().request(request).build());
        return chain.filter(exchange);

    }
}

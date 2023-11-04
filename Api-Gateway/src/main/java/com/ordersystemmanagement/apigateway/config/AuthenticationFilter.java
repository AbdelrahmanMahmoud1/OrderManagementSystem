package com.ordersystemmanagement.apigateway.config;
//
//import com.ordersystemmanagement.apigateway.jwtservice.JwtService;
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//@RequiredArgsConstructor
//public class AuthenticationFilter implements GatewayFilter {
//
//    private RouterValidator routerValidator;
//    private JwtService jwtService;
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        ServerHttpRequest request = exchange.getRequest();
//
//        if (routerValidator.isSecured.test(request)) {
//            if (this.isAuthMissing(request))
//                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
//
//            final String token = this.getAuthHeader(request);
//
//            if (jwtService.isTokenValid(token))
//                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
//
//            this.populateRequestWithHeaders(exchange, token);
//        }
//        return chain.filter(exchange);
//
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(httpStatus);
//        return response.setComplete();
//    }
//
//    private String getAuthHeader(ServerHttpRequest request) {
//        return request.getHeaders().getOrEmpty("Authorization").get(0);
//    }
//
//    private boolean isAuthMissing(ServerHttpRequest request) {
//        return !request.getHeaders().containsKey("Authorization");
//    }
//
//    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
//        Claims claims = jwtService.extractAllClaims(token);
//        exchange.getRequest().mutate()
//                .header("id", String.valueOf(claims.get("id")))
//                .header("role", String.valueOf(claims.get("role")))
//                .build();
//    }
//}

import com.ordersystemmanagement.apigateway.jwtservice.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    final Logger logger =
            LoggerFactory.getLogger(AuthenticationFilter.class);
    private final RouterValidator routerValidator;
    private final JwtService jwtService;

    public static class Config{

    }

    @Autowired
    public AuthenticationFilter(RouterValidator routerValidator, JwtService jwtService){
        super(Config.class);
        this.routerValidator = routerValidator;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {
        return ((exchange, chain) -> {

            if (routerValidator.isSecured.test(exchange.getRequest())){
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Auth Header");
                }
                String authHeader= Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
                if (authHeader!=null && authHeader.startsWith("Bearer")){
                    authHeader=authHeader.substring(7);
                }
                try {
                    System.out.println("ana hena");
                    jwtService.isTokenValid(authHeader);

                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
            logger.info("AUthentocaton filter   executed abdelrahman mahmouddddsssssssssssssssssssssss");
            return  chain.filter(exchange);
        });
    }
}


package com.ordersystemmanagement.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LoggingGlobalPreFilter implements GlobalFilter {

    private final RouterValidator routerValidator;

    final Logger logger =
            LoggerFactory.getLogger(LoggingGlobalPreFilter.class);

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {
        if (!routerValidator.isSecured.test(exchange.getRequest())){
            return chain.filter(exchange);
        }
//
//        if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION) != null){
//            throw new RuntimeException("No Auth Header ");
//
//        }
//        String authHeader= Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));


        return chain.filter(exchange);
    }
}


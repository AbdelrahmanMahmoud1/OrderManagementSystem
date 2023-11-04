package com.ordersystemmanagement.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {


//    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route( r -> r.path("/api/v1/auth/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://user-management"))
                .route( r -> r.path("/api/demo")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://user-management"))
                .build();
    }

}
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
                // TODO: 11/12/2023 quick tip you can define it in application.yml or application.properties https://stackoverflow.com/questions/75912883/how-to-specify-a-route-in-a-spring-boot-application

                .route( r -> r.path("/api/v1/auth/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://user-management"))
                .route( r -> r.path("/api/demo")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://user-management"))
                .route( r -> r.path("/products/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://inventory-service"))
                .route( r -> r.path("/categories/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://inventory-service"))
                .route( r -> r.path("/buying/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://purchasing-management-service"))
                .route( r -> r.path("/selling/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://purchasing-management-service"))
                .route( r -> r.path("/api/order")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://order-service"))
                .build();



    }

}
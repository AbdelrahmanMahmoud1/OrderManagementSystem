package com.ordersystemmanagement.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    @Value("${auth.open.endpoint}")
    private  static String authOpenEndPoint;
    public static final List<String> openApiEndpoints = List.of(
            authOpenEndPoint
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
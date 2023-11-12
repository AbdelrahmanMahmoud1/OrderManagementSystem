package com.ordersystemmanagement.apigateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    // TODO: 11/12/2023 extract them to application.properties as a property
    public static final List<String> openApiEndpoints = List.of(
            "api/v1/auth/register",
            "api/v1/auth/authenticate",
            "/api/v1/auth/**"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
package com.ordersystemmanagement.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

//    private static String authOpenEndPoint= "/api/v1/auth/**";
    public static final List<String> openApiEndpoints = List.of(
          "/api/v1/auth/register","/api/v1/auth/authenticate"
    );

    public static final List<String> securedApiEndpoints = List.of(
            "/test",
            "/products"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isSecuredAuthorization =
            request -> securedApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

}
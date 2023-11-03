package com.ordersystemmanagement.apigateway.api;


import com.ordersystemmanagement.apigateway.entity.User;
import com.ordersystemmanagement.apigateway.requestandresponsetemplate.AuthenticationRequest;
import com.ordersystemmanagement.apigateway.requestandresponsetemplate.AuthenticationResponse;
import com.ordersystemmanagement.apigateway.requestandresponsetemplate.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name="user-management",url="localhost:8000/api/v1/auth")
public interface UserManagementProxy {

    @PostMapping("/register")
     User register(
            @RequestBody RegisterRequest user
    );

    @PostMapping("/authenticate")
     User authenticate(
            @RequestBody AuthenticationRequest request
    );

    @GetMapping("/user")
    Optional<User> getUser(
            @RequestBody String email
    );
}

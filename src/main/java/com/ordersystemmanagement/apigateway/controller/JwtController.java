package com.ordersystemmanagement.apigateway.controller;

import com.ordersystemmanagement.apigateway.api.UserManagementProxy;
import com.ordersystemmanagement.apigateway.entity.User;
import com.ordersystemmanagement.apigateway.jwtservice.JwtService;
import com.ordersystemmanagement.apigateway.jwtservice.JwtTokenGenerator;
import com.ordersystemmanagement.apigateway.requestandresponsetemplate.AuthenticationRequest;
import com.ordersystemmanagement.apigateway.requestandresponsetemplate.AuthenticationResponse;
import com.ordersystemmanagement.apigateway.requestandresponsetemplate.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class JwtController {

    private final UserManagementProxy userManagementProxy;
    private final JwtTokenGenerator jwtTokenGenerator;


    @GetMapping("/test")
    public String hello(){
        return "hello";
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        System.out.println(request);
        User user = userManagementProxy.register(request);
        return ResponseEntity.ok(jwtTokenGenerator.generateToken(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        try {
            User user = userManagementProxy.authenticate(request);
            return ResponseEntity.ok(jwtTokenGenerator.generateToken(user));

        }catch (Exception e){
            System.out.println("User Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponse("NOT FOUND"));

        }
    }
}

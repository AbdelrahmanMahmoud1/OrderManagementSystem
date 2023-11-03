package com.ordermanagementsystem.usermanagement.userservice;

import com.ordermanagementsystem.usermanagement.jwtservice.AuthenticationService;
import com.ordermanagementsystem.usermanagement.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @GetMapping("/test")
    public String hello(){
        return "hello";
    }


    @PostMapping("/register")
    public User register(
            @RequestBody RegisterRequest request

    ){
        System.out.println(request);

        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public User register(
            @RequestBody AuthenticationRequest request
    ){
        return authenticationService.authenticate(request);
    }
}

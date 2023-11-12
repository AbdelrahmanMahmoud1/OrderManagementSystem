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
// TODO: 11/12/2023 remove to controller package
public class UserController {

    private final AuthenticationService authenticationService;

    // TODO: 11/12/2023 remove it
    @GetMapping("/test")
    public String hello(){
        return "hello";
    }


    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request

    ){
        System.out.println(request);

        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public String register(
            @RequestBody AuthenticationRequest request
    ){
        return authenticationService.authenticate(request);
    }
}

package com.ordersystemmanagement.apigateway.jwtservice;

import com.ordersystemmanagement.apigateway.entity.User;
import com.ordersystemmanagement.apigateway.requestandresponsetemplate.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// TODO: 11/12/2023 why is it here and not in use-management

public class JwtTokenGenerator {
    private final JwtService jwtService;

    public  AuthenticationResponse generateToken(User user) {


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}

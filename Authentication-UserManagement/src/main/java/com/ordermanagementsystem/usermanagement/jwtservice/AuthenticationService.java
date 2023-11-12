package com.ordermanagementsystem.usermanagement.jwtservice;


import com.ordermanagementsystem.usermanagement.user.Role;
import com.ordermanagementsystem.usermanagement.user.User;
import com.ordermanagementsystem.usermanagement.userservice.AuthenticationRequest;
import com.ordermanagementsystem.usermanagement.userservice.RegisterRequest;
import com.ordermanagementsystem.usermanagement.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    public String authenticate(AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userService.findByEmail(request.getEmail()).orElseThrow();
            System.out.println(user);

            return jwtService.generateToken(user);

        }catch (Exception e){
            System.out.println("User not found");
            return null;
        }


    }

    public  String register(RegisterRequest request) {
        // TODO: 11/12/2023 nice use of var and builder *clap* *clap*
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userService.saveUser(user);

        return jwtService.generateToken(user);

    }
}

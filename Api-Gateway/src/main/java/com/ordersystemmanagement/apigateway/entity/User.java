package com.ordersystemmanagement.apigateway.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    // TODO: 11/12/2023 not needed
    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
package com.ordersystemmanagement.apigateway.requestandresponsetemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// TODO: 11/12/2023 not used remove 
public class AuthenticationRequest {
    private String email;
    private String password;
}

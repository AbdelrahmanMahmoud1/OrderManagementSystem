package com.ordersystemmanagement.apigateway.requestandresponsetemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// TODO: 11/12/2023 wrong place not role of gateway to handle authentication response
public class AuthenticationResponse {

    private String token;

}

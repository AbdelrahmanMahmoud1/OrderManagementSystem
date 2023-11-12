package com.giza.purshasingmanagement.controller.selling.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class SubmitOrderResponse {
    private Map<String, Double> productRevenuePair;
    private String message;
}

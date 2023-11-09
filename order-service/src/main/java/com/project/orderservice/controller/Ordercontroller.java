package com.project.orderservice.controller;

import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class Ordercontroller {

    //now we need to call this order service from the controller
    private final OrderService orderService;

    public Ordercontroller(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String placeOrder(@RequestBody OrderRequest orderRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        try {
            orderService.placeOrder(orderRequest, auth);

            return "Order placed Successfully";
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return "Order placement failed: " + e.getMessage();
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}

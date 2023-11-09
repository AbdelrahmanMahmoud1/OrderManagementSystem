package com.project.orderservice.controller;

import com.project.orderservice.config.OrderProducer;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlacedEvent;
import com.project.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class Ordercontroller {

    //now we need to call this order service from the controller
    private final OrderService orderService;

    private final OrderProducer orderProducer;


    @Autowired
    public Ordercontroller(OrderService orderService, OrderProducer orderProducer) {
        this.orderService = orderService;
        this.orderProducer = orderProducer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String placeOrder(@RequestBody OrderRequest orderRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        try {
            orderService.placeOrder(orderRequest, auth);
            orderProducer.sendMessage(new OrderPlacedEvent());
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

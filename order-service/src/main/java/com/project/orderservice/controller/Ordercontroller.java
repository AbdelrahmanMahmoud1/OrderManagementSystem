package com.project.orderservice.controller;

import com.project.orderservice.config.OrderProducer;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlacedEvent;
import com.project.orderservice.service.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class Ordercontroller {

    //now we need to call this order service from the controller
    private final OrderService orderService;
    private  final OrderProducer orderProducer;

    public Ordercontroller(OrderService orderService, OrderProducer orderProducer) {
        this.orderService = orderService;
        this.orderProducer = orderProducer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String placeOrder(@RequestBody OrderRequest orderRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        try {
            orderService.placeOrder(orderRequest,auth);
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setStatus("Pending");
            orderPlacedEvent.setMessage("Order is pending");
            orderProducer.sendMessage(orderPlacedEvent);
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

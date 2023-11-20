package com.project.orderservice.controller;

import com.project.orderservice.config.OrderProducer;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlacedEvent;
import com.project.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/order")
public class Ordercontroller {

    //now we need to call this order service from the controller
    private final OrderService orderService;
    private  final OrderProducer orderProducer;
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
    // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    // TODO: 11/12/2023 why put @requestheader autorization?
    public String placeOrder(@RequestBody OrderRequest orderRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        try {
            // TODO: 11/12/2023 move logic to business service  class
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

    // TODO: 11/12/2023 remove
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}

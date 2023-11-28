package com.project.orderservice.controller;
import com.project.orderservice.config.OrderProducer;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlacedEvent;
import com.project.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/order")
public class Ordercontroller {

    //now we need to call this order service from the controller
    @Autowired
    private final OrderService orderService;
    @Autowired
    private  final OrderProducer orderProducer;

    @PostMapping
    @CrossOrigin
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request,String auth) {
        try {
            orderService.placeOrder(request,auth);
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setStatus("Pending");
            orderPlacedEvent.setMessage("Order is pending");
            orderProducer.sendMessage(orderPlacedEvent);
           return ResponseEntity.ok().body("{\"message\": \"order placed successfully\"}");
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
          return  ResponseEntity.ok().body("{\"message\": \"order placed successfully\"}"+e);
        }
    }

}

package com.project.orderservice.event;

import com.project.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderPlacedEvent {

    private String orderNumber;


    private String message;
    private String status;
    private Order order;
}

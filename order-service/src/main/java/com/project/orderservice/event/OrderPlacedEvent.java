package com.project.orderservice.event;

import com.project.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class OrderPlacedEvent {

    private String orderNumber;


    private String message;
    private String status;
    private Order order;

    public OrderPlacedEvent() {
    }

    public OrderPlacedEvent(String orderNumber, String message, String status, Order order) {
        this.orderNumber = orderNumber;
        this.message = message;
        this.status = status;
        this.order = order;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

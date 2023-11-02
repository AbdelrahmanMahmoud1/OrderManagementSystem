package com.project.orderservice.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    private String OrderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderLineItems>orderLineItemsList;

    public Order() {
    }

    public Order(int id, String orderNumber, List<OrderLineItems> orderLineItemsList) {
        this.id = id;
        OrderNumber = orderNumber;
        this.orderLineItemsList = orderLineItemsList;
    }

    public Order(String orderNumber, List<OrderLineItems> orderLineItemsList) {
        OrderNumber = orderNumber;
        this.orderLineItemsList = orderLineItemsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public List<OrderLineItems> getOrderLineItemsList() {
        return orderLineItemsList;
    }

    public void setOrderLineItemsList(List<OrderLineItems> orderLineItemsList) {
        this.orderLineItemsList = orderLineItemsList;
    }
}

package com.project.orderservice.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="orders")
public class Order {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // TODO: 11/12/2023 never assign id to an int
    private int id;

    @Column(name = "number")
    private String OrderNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderLineItems> products;

    public Order() {
    }

    public Order(int id, String orderNumber, List<OrderLineItems> products) {
        this.id = id;
        OrderNumber = orderNumber;
        this.products = products;
    }

    public Order(String orderNumber, List<OrderLineItems> products) {
        OrderNumber = orderNumber;
        this.products = products;
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

    public List<OrderLineItems> getProducts() {
        return products;
    }

    public void setProducts(List<OrderLineItems> products) {
        this.products = products;
    }
}

package com.headwayproject.inventoryservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
  // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;

    @Column(name = "name")
    private String name;

   @ManyToOne
   @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "price")
    private float price;

    @Column(name = "quantity")
    private int quantity;

    public Product() {
    }

    public Product(String name, Category category, float price, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

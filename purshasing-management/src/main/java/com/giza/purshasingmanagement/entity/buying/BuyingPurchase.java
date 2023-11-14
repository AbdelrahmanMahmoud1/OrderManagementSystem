package com.giza.purshasingmanagement.entity.buying;

import com.giza.purshasingmanagement.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "buying-purchase")
@Getter
@Setter
@RequiredArgsConstructor
public class BuyingPurchase implements Serializable {
    @Id
    //TODO: Ask Mohanad, identity gives an error auto doesn't
    // Table is not an identity
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private long purchaseId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "products")
    @ElementCollection(targetClass = Product.class)
    // TODO: 11/11/2023 define relation is it OneToMany or ManyToMany so you can handle cascading https://www.baeldung.com/jpa-many-to-many https://www.baeldung.com/hibernate-one-to-many
    private List<Product> products;
    // Handled using cascade

    @Column(name = "cost")
    private double cost;
}

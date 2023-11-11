package com.giza.purshasingmanagement.entity.buying;

import com.giza.purshasingmanagement.entity.db.ProductDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "buying-purchase")
public class BuyingPurchase implements Serializable {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok


    @Id
    // TODO: 11/11/2023 make it Identity instead of auto
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private long purchaseId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "products")
    @ElementCollection(targetClass = ProductDB.class)
    // TODO: 11/11/2023 define relation is it OneToMany or ManyToMany so you can handle cascading https://www.baeldung.com/jpa-many-to-many https://www.baeldung.com/hibernate-one-to-many
    private List<ProductDB> products;

    @Column(name = "cost")
    private double cost;

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<ProductDB> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDB> products) {
        this.products = products;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

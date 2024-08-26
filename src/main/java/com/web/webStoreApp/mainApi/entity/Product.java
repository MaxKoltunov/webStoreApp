package com.web.webStoreApp.mainApi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "products", schema = "mainschema")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_seq")
    @SequenceGenerator(name = "products_id_seq", sequenceName = "mainschema.products_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "arrival_date", nullable = false)
    private Timestamp arrivalDate;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private ExistingDiscount existingDiscount;


    public Product(String name, String type, String brand, Long cost, Timestamp arrivalDate, Long amount, ExistingDiscount existingDiscount) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.cost = cost;
        this.arrivalDate = arrivalDate;
        this.amount = amount;
        this.existingDiscount = existingDiscount;
    }

    public Product(String name, String type, String brand, Long cost, Timestamp arrivalDate) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.cost = cost;
        this.arrivalDate = arrivalDate;
    }
}

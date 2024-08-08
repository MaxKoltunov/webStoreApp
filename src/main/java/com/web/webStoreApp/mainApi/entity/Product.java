package com.web.webStoreApp.mainApi.entity;

import jakarta.persistence.*;



@Entity
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
    private String arrivalDate;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private ExistingDiscount existingDiscount;


    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public ExistingDiscount getDiscount() {
        return existingDiscount;
    }

    public void setDiscount(ExistingDiscount existingDiscount) {
        this.existingDiscount = existingDiscount;
    }
}

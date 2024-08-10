package com.web.webStoreApp.mainApi.dto;

import java.sql.Timestamp;

public class ProductDTO {

    private String name;

    private String type;

    private Long cost;

    private String brand;

    private Timestamp arrival_date;

    private Long discountId;

    private Long amount;


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

    public Timestamp getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(Timestamp arrival_date) {
        this.arrival_date = arrival_date;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }
}

package com.web.webStoreApp.mainApi.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Cart", schema = "mainschema")
@IdClass(CartId.class)
public class Cart {


    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "product_id", nullable = false)
    private Long productId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
//    private Product product;

    @Column(name = "amount", nullable = false)
    private Long amount;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}

package com.web.webStoreApp.mainApi.entity;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {

    private Long userId;

    private Long productId;


    public CartId() {

    }

    public CartId(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return Objects.equals(userId, cartId.userId) && Objects.equals(productId, cartId.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}

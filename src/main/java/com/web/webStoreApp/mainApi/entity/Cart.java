package com.web.webStoreApp.mainApi.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "amount", nullable = false)
    private Long amount;
}

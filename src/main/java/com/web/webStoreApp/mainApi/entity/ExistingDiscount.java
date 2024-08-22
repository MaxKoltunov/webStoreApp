package com.web.webStoreApp.mainApi.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discounts", schema = "mainschema")
public class ExistingDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discounts_id_seq")
    @SequenceGenerator(name = "discounts_id_seq", sequenceName = "mainschema.discounts_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @OneToMany(mappedBy = "existingDiscount")
    private Set<Product> products;

    public ExistingDiscount(String name, String type, String productType, Timestamp startDate, Timestamp endDate) {
        this.name = name;
        this.type = type;
        this.productType = productType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

package com.web.webStoreApp.mainApi.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String name;

    private String type;

    private Long cost;

    private String brand;

    private Timestamp arrivalDate;

    private Long discountId;

    private Long amount;
}

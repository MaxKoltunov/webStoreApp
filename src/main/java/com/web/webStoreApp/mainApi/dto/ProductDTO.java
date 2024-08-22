package com.web.webStoreApp.mainApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;

    private String type;

    private Long cost;

    private String brand;

    private Timestamp arrivalDate;

    private Long discountId;

    private Long amount;
}

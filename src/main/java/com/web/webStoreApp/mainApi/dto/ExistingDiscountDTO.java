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
public class ExistingDiscountDTO {

    private Long id;

    private String name;

    private String type;

    private String productType;

    private Timestamp startDate;

    private Timestamp endDate;
}

package com.web.webStoreApp.mainApi.dto;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExistingDiscountDTO {

    private Long id;

    private String name;

    private String type;

    private String productType;

    private Timestamp startDate;

    private Timestamp endDate;
}

package com.web.webStoreApp.mainApi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {

    private Long userId;

    private Long productId;

    private Long amount;
}

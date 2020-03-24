package com.coviam.team3bookstorebackend.orderMS.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartUpdateDTO {
    private String cartId;
    private String customerId;
}

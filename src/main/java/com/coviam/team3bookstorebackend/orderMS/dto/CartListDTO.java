package com.coviam.team3bookstorebackend.orderMS.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartListDTO {
    private String orderId;
    private String quantity;
    private String productName;
    private String author;
    private String price;
    private String url;
    private String productId;
    private String merchantId;

}

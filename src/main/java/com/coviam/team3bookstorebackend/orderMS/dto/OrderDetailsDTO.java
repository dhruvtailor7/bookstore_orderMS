package com.coviam.team3bookstorebackend.orderMS.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO
{
    private String orderId;
    private String merchantId;
    private String productId;
    private String quantity;
    private String price;
}

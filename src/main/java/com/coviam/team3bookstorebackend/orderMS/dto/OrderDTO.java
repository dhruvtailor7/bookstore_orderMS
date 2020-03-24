package com.coviam.team3bookstorebackend.orderMS.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO
{

    //private String orderId;
    private String orderId;
    private String timeStamp;
    private String customerEmail;
    private String customerId;
    private String cost;
}

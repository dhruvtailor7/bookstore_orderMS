package com.coviam.team3bookstorebackend.orderMS.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderHistoryDTO
{
    private String orderId;
    private String timeStamp;
}

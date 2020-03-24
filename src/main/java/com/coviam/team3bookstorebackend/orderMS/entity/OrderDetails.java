package com.coviam.team3bookstorebackend.orderMS.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="OrderDetails")
@Getter
@Setter
@ToString
public class OrderDetails
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String orderDetailsId;
    private String orderId;
    private String merchantId;
    private String productId;
    private String quantity;
    private String price;



}

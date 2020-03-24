package com.coviam.team3bookstorebackend.orderMS.entity;


import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="OrderTable")
@ToString
@lombok.Getter
@lombok.Setter
public class Order
{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String orderId;
    private String timeStamp;
    private String customerEmail;
    private String customerId;
    private String cost;
}

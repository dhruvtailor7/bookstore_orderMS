package com.coviam.team3bookstorebackend.orderMS.repositery;

import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepositery extends CrudRepository<Order,String>
{

}

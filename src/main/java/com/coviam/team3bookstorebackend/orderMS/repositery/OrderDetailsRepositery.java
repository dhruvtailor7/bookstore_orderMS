package com.coviam.team3bookstorebackend.orderMS.repositery;

import com.coviam.team3bookstorebackend.orderMS.entity.OrderDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDetailsRepositery extends CrudRepository<OrderDetails,String> {
}

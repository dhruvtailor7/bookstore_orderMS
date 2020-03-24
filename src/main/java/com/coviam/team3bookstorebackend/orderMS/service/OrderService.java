package com.coviam.team3bookstorebackend.orderMS.service;

import com.coviam.team3bookstorebackend.orderMS.dto.CheckOutDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.OrderDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.OrderHistoryDTO;
import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.entity.OrderDetails;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);

    ArrayList<OrderDetails> getOrder(String order_id);

    void sendEmail(OrderDTO ordercreated) throws MessagingException, IOException;

    OrderDetails saveDetails(OrderDetails orderDetails);

    ArrayList<OrderDetails> getOrderDetailsByCustomerId(String customer_id);

    ArrayList<OrderHistoryDTO> getOrderHistoryByCustomerId(String customer_id);

    ArrayList<CheckOutDTO> getOrderDetailsByOrderId(String order_id);
}

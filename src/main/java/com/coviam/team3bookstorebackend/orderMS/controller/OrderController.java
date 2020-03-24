package com.coviam.team3bookstorebackend.orderMS.controller;

import com.coviam.team3bookstorebackend.orderMS.client.CartClient;
import com.coviam.team3bookstorebackend.orderMS.client.CustomerClient;
import com.coviam.team3bookstorebackend.orderMS.client.MerchantClient;
import com.coviam.team3bookstorebackend.orderMS.client.ProductClient;
import com.coviam.team3bookstorebackend.orderMS.dto.*;
import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.entity.OrderDetails;
import com.coviam.team3bookstorebackend.orderMS.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping
public class OrderController
{
    @Autowired
    OrderService orderService;


    @Autowired
    OrderController orderController;

    @Autowired
    MerchantClient merchantClient;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    CartClient cartClient;

    @Autowired
    ProductClient productClient;


   // @PostMapping(value = "/addOrder")
    private String addOrder( OrderDTO orderDTO) throws IOException, MessagingException {

        Order order = new Order();
        BeanUtils.copyProperties(orderDTO,order);
        if(orderDTO.getOrderId()!=null) {
            order.setOrderId(orderDTO.getOrderId());
        }
        System.out.println(order);
        Order orderCreated = orderService.save(order);
        return orderCreated.getOrderId();

    }

    //@PostMapping(value = "/addOrderDetails")
    private String addOrderDetails(CheckOutDTO checkOutDTO) throws IOException, MessagingException
    {
        OrderDetails orderDetails=new OrderDetails();
        BeanUtils.copyProperties(checkOutDTO,orderDetails);
        orderDetails.setOrderId(checkOutDTO.getOrderId());
        OrderDetails orderDetailsCreated=orderService.saveDetails(orderDetails);
        System.out.println(orderDetails);
        return "success";

    }



   // @GetMapping(value = "/getOrderDetails/{id}")
    public ArrayList<OrderDetails> getOrderDetails( String order_id)
    {
        return orderService.getOrder(order_id);

    }


  // @PostMapping(value = "/generateEmail")
    public void sendEmail(OrderDTO orderDTO) throws Exception {

       System.out.println("............Email"+orderDTO.getOrderId());
        orderService.sendEmail(orderDTO);
       System.out.println("............Email");

    }

     @GetMapping(value = "/getOrderHistoryByCustomerId/{id}")
    public ArrayList<OrderHistoryDTO> getOrderHistoryByCustomerId(@PathVariable("id") String customer_id)
    {
        return orderService.getOrderHistoryByCustomerId(customer_id);
    }
 
    @GetMapping(value = "/getOrderDetailsbyOrderId/{id}")
    public ArrayList<CheckOutDTO> getOrderDetailsByOrderId(@PathVariable("id")String order_id)
    {
        return orderService.getOrderDetailsByOrderId(order_id);
    }


   // @GetMapping(value = "/getOrderDetailsByCustomerId/{id}")
    public ArrayList<OrderDetails> getOrderDetailsByCustomerId(String customer_id)
    {
        return orderService.getOrderDetailsByCustomerId(customer_id);
    }

    @GetMapping(value = "/checkout/{id}")
    synchronized public List<CheckOutDTO> checkout(@PathVariable("id") String cartId) throws Exception {
        System.out.println(cartId);
        List<CartListDTO> cartList=(ArrayList<CartListDTO>)cartClient.getByCartId(cartId);
        RemoveAllDTO removeAllDTO=new RemoveAllDTO();
        removeAllDTO.setCartId(cartId);
        cartClient.removeAll(removeAllDTO);
        double cost=0;
        OrderDTO orderDTO = new OrderDTO();
        String orderId1=null;
        System.out.println(cartList);
        List<CheckOutDTO> checkOutDTOList=new ArrayList<>();

        for (CartListDTO cartListDTO:cartList) {
         //   cost += Double.parseDouble(cartListDTO.getPrice()) * Double.parseDouble(cartListDTO.getQuantity());
            QuantityUpdateDTO quantityUpdateDTO = new QuantityUpdateDTO();
            BeanUtils.copyProperties(cartListDTO, quantityUpdateDTO);
            //System.out.println(quantityUpdateDTO);
            //System.out.println(cartListDTO);
            if (!merchantClient.checkQuantity(quantityUpdateDTO)) {
                for (CartListDTO cartListDTO1:cartList) {
                    CartDTO cartDTO=new CartDTO();
                    cartDTO.setCartId(cartId);
                    cartDTO.setMerchantId(cartListDTO1.getMerchantId());
                    cartDTO.setProductId(cartListDTO.getProductId());
                    cartDTO.setQuantity(cartListDTO1.getQuantity());
                    cartClient.add(cartDTO);
                }
                return null;
            }

        }
         for (CartListDTO cartListDTO:cartList) {
           cost+=Double.parseDouble(cartListDTO.getPrice())*Double.parseDouble(cartListDTO.getQuantity());
            QuantityUpdateDTO quantityUpdateDTO = new QuantityUpdateDTO();
            BeanUtils.copyProperties(cartListDTO, quantityUpdateDTO);
            System.out.println(quantityUpdateDTO);
            System.out.println(cartListDTO);

            if(orderId1!=null)
                cartListDTO.setOrderId(orderId1);
           // Boolean quantityCheck = merchantClient.checkQuantity(quantityUpdateDTO);
           // if (merchantClient.checkQuantity(quantityUpdateDTO)) {

                BeanUtils.copyProperties(cartListDTO, orderDTO);
                DateFormat df=new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date date=new Date();
                orderDTO.setTimeStamp(df.format(date));
                orderDTO.setCustomerEmail(customerClient.getEmailById(cartId));
                orderDTO.setCost(String.valueOf(cost));
                orderDTO.setCustomerId(cartId);
                System.out.println(orderDTO);

                if(orderId1!=null)
                    orderDTO.setOrderId(orderId1);


                //String email = customerClient.getEmailById(checkoutDTO.getCustomerId());
               // orderDTO.setCustomerEmail(email);

                    orderId1 = this.addOrder(orderDTO);
                System.out.println("OrderId generated is : "+orderId1);
                    orderDTO.setOrderId(orderId1);




                CheckOutDTO checkOutDTO=new CheckOutDTO();
                BeanUtils.copyProperties(cartListDTO,checkOutDTO);
                checkOutDTO.setCost(String.valueOf(cost));
                checkOutDTO.setOrderId(orderId1);
                checkOutDTO.setTimeStamp(df.format(date));
                this.addOrderDetails(checkOutDTO);
                System.out.println(orderDTO);
                merchantClient.updateQuantity(quantityUpdateDTO);

                ProductDetailsDTO productDetailsDTO=productClient.getProductById(cartListDTO.getProductId());

                checkOutDTO.setProductName(productDetailsDTO.getProductName());
                checkOutDTO.setUrl(productDetailsDTO.getUrl());
                checkOutDTOList.add(checkOutDTO);


        }
        this.sendEmail(orderDTO);



        return checkOutDTOList;

    }



}

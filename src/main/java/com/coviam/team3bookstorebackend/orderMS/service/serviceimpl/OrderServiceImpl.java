package com.coviam.team3bookstorebackend.orderMS.service.serviceimpl;

import com.coviam.team3bookstorebackend.orderMS.client.CustomerClient;
import com.coviam.team3bookstorebackend.orderMS.client.ProductClient;
import com.coviam.team3bookstorebackend.orderMS.dto.CheckOutDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.OrderDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.OrderHistoryDTO;
import com.coviam.team3bookstorebackend.orderMS.entity.Order;
import com.coviam.team3bookstorebackend.orderMS.entity.OrderDetails;
import com.coviam.team3bookstorebackend.orderMS.repositery.OrderDetailsRepositery;
import com.coviam.team3bookstorebackend.orderMS.repositery.OrderRepositery;
import com.coviam.team3bookstorebackend.orderMS.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepositery orderRepositery;


    @Autowired
    OrderDetailsRepositery orderDetailsRepositery;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Override
    public OrderDetails saveDetails(OrderDetails orderDetails) {
        return orderDetailsRepositery.save(orderDetails);
    }

    @Override
    public Order save(Order order) {
//        ArrayList<Order> orderArrayList=(ArrayList<Order>)orderRepositery.findAll();
//        orderArrayList=(ArrayList<Order>)orderArrayList.stream().filter(order1 -> order1.getCustomerEmail()
//        .equals(order.getCustomerEmail())).collect(Collectors.toList());
//        Order order1=new Order();
//        if(orderArrayList.get(0).getCustomerEmail()!=null)
//        {
//            order1.setOrderId(orderArrayList.get(0).getOrderId());
//            order1.setCost(orderArrayList.get(0).getCost());
//            order1.setCustomerEmail(orderArrayList.get(0).getCustomerEmail());
//            order1.setCustomerId(orderArrayList.get(0).getCustomerId());
//            order1.setTimeStamp(orderArrayList.get(0).getTimeStamp());
//            return orderRepositery.save(order1);
//
//        }

        return orderRepositery.save(order);
    }

    @Override
    public ArrayList<OrderDetails> getOrder(String order_id)
    {
        ArrayList<OrderDetails> orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsRepositery.findAll();

        orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsArrayList.stream().filter(orderDetails ->
                orderDetails.getOrderId().equals(order_id)).collect(Collectors.toList());

        return orderDetailsArrayList;




        //return orderRepositery.findById(order_id);
    }


    public ArrayList<OrderDetails> getOrderDetailsByCustomerId(String customer_id)
    {
            ArrayList<Order> orderArrayList=(ArrayList<Order>)orderRepositery.findAll();
            orderArrayList=(ArrayList<Order>) orderArrayList.stream().filter(order ->
                    order.getCustomerId().equals(customer_id)).collect(Collectors.toList());

            ArrayList<OrderDetails> orderDetailsArrayList=new ArrayList<>();

        for (Order order:orderArrayList)
        {
            ArrayList<OrderDetails> orderDetailsArrayList1=(ArrayList<OrderDetails>)orderDetailsRepositery.findAll();
            orderDetailsArrayList1=(ArrayList<OrderDetails>)orderDetailsArrayList1.stream().filter(orderDetails ->
                    orderDetails.getOrderId().equals(order.getOrderId())).collect(Collectors.toList());

            for (OrderDetails orderDetails:orderDetailsArrayList1)
            {
                orderDetailsArrayList.add(orderDetails);

            }
        }
        return orderDetailsArrayList;
    }

    @Override
    public void sendEmail(OrderDTO ordercreated) throws AddressException, MessagingException, IOException
    {



        Order order=orderRepositery.findById(ordercreated.getOrderId()).get();

        ArrayList<OrderDetails> orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsRepositery.findAll();




        orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsArrayList.stream().filter(orderDetails ->
            orderDetails.getOrderId().equals(ordercreated.getOrderId())).collect(Collectors.toList());

        System.out.println(order.getOrderId());
        //order.setOrderId(order_id);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("book.adda007@gmail.com", "ajgoyicabhtgqdkv\n\n"); }});
                                                                //pritesh.radadiya@coviam.com//azcxhncltpvwfpoe
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("book.adda007@gmail.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ordercreated.getCustomerEmail()));//"priteshradadiya01@gmail.com"
        msg.setSubject("Your Order is confirmed ...\nYour Order id is:"+order.getOrderId()+"\n");
        msg.setContent("Order Summary", "text/html");
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        String name=customerClient.getName(ordercreated.getCustomerId());
        String orderinfo="Dear    "+name+",";
        for (OrderDetails orderDetails:orderDetailsArrayList) {

            String productName=productClient.getProductNameByProductId(orderDetails.getProductId());
            String productURL=productClient.getProductURLByProductId(orderDetails.getProductId());
            double cost=Double.parseDouble(orderDetails.getPrice())*Double.parseDouble(orderDetails.getQuantity());


            orderinfo=orderinfo+"<br><div style='border-style:solid'><img src='"+productURL
                    +"' style='max-height:300px;'><div style='border-style:solid'><table><tr><td>ProductName</td><td> : </td><td>"+
                    "<font color=red>"+productName+"</font>"+"</td></tr><tr><td>QUANTITY</td><td>:</td><td>"+
                    "<font color=red>"+orderDetails.getQuantity()+"</font>"+
                    "</td></tr><tr><td>PRICE</td><td>:</td><td>" +
                    "<font color=red>"+orderDetails.getPrice()+"</font>"+"</td></tr><tr><td>Cost</td><td>:</td><td>"+
                    "<font color=red>"+cost+"</font>"+"</td></tr><table></div></div><br>";

        }

        messageBodyPart.setContent(orderinfo, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        //MimeBodyPart attachPart = new MimeBodyPart();
        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
        System.out.println("email sent successfully !");
    }


    @Override
    public ArrayList<OrderHistoryDTO> getOrderHistoryByCustomerId(String customer_id)
    {
       // String email=customerClient.getEmailById(customer_id);
        System.out.println(customer_id);
        ArrayList<Order> orderArrayList=(ArrayList<Order>)orderRepositery.findAll();
        orderArrayList=(ArrayList<Order>)orderArrayList.stream().filter(order
                -> order.getCustomerId().equals(customer_id)).collect(Collectors.toList());
        ArrayList<OrderHistoryDTO> orderHistoryDTOS=new ArrayList<>();

        System.out.println(orderArrayList);

        for (Order order:orderArrayList)
        {
            OrderHistoryDTO orderHistoryDTO=new OrderHistoryDTO();
            orderHistoryDTO.setOrderId(order.getOrderId());
            orderHistoryDTO.setTimeStamp(order.getTimeStamp());
            orderHistoryDTOS.add(orderHistoryDTO);

        }
        return orderHistoryDTOS;
    }


    @Override
    public ArrayList<CheckOutDTO> getOrderDetailsByOrderId(String order_id) {
        ArrayList<OrderDetails> orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsRepositery.findAll();
        orderDetailsArrayList=(ArrayList<OrderDetails>)orderDetailsArrayList.stream().filter(orderDetails ->
        orderDetails.getOrderId().equals(order_id)).collect(Collectors.toList());


        ArrayList<CheckOutDTO> checkOutDTOArrayList=new ArrayList<>();

        for (OrderDetails orderDetails:orderDetailsArrayList)
        {
            Double cost=Double.parseDouble(orderDetails.getPrice())*Double.parseDouble(orderDetails.getQuantity());
            CheckOutDTO checkOutDTO=new CheckOutDTO();
            BeanUtils.copyProperties(orderDetails,checkOutDTO);
            checkOutDTO.setUrl(productClient.getProductURLByProductId(orderDetails.getProductId()));
            checkOutDTO.setProductName(productClient.getProductNameByProductId(orderDetails.getProductId()));
            checkOutDTO.setCost(String.valueOf(cost));
            checkOutDTOArrayList.add(checkOutDTO);

        }
        return checkOutDTOArrayList;

    }
}

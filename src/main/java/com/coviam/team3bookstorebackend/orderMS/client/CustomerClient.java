package com.coviam.team3bookstorebackend.orderMS.client;


//import org.springframework.cloud.netflix.feign.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "customer")
public interface CustomerClient {

//    @PostMapping("/add")
//    String add(@RequestBody CustomerDTO customerDTO);
//
//    @GetMapping("/getCustomerById/{id}")
//    CustomerDTO getCustomerById(@PathVariable("id") String customerId);

    @GetMapping("/getEmailById/{id}")
    String getEmailById(@PathVariable("id") String customerId);

    @GetMapping(value = "/getNameById/{Id}")
    public String getName(@PathVariable("Id") String customer_id);
}

package com.coviam.team3bookstorebackend.orderMS.client;


import com.coviam.team3bookstorebackend.orderMS.dto.ProductDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("http://bookstore-product.herokuapp.com/")
public interface ProductClient {






    @GetMapping("/getProductByProductId/{id}")
    ProductDetailsDTO getProductById(@PathVariable("id") String id);

    @GetMapping("/getProductURLByProductId/{Id}")
    String getProductURLByProductId(@PathVariable("Id") String productId);

    @GetMapping("/getProductNameByProductId/{Id}")
    String getProductNameByProductId(@PathVariable("Id") String productId);





}

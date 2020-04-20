package com.coviam.team3bookstorebackend.orderMS.client;


import com.coviam.team3bookstorebackend.orderMS.dto.QuantityUpdateDTO;
//import org.springframework.cloud.netflix.feign.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("http://bookstore-merchant.herokuapp.com/")
public interface MerchantClient {
//    @PostMapping("/addMerchant")
//    String addMerchant(@RequestBody MerchantDTO merchantDTO);
//
//    @PostMapping("/addProductMerchant")
//    String addProductMerchant(@RequestBody ProductMerchantDTO productMerchantDTO);
//
//    @DeleteMapping("/removeProduct")
//    String removeProduct(@RequestBody RemoveProductDTO removeProductDTO);
//
//    @GetMapping("/getMerchantById/{id}")
//    MerchantDTO getMerchantById(@PathVariable("id") String merchantId);
//
//    @PostMapping("/addMerchantRating")
//    String addMerchantRating(@RequestBody MerchantRatingDTO merchantRatingDTO);
//
//    @GetMapping("/getProductMerchantByMerchantId/{id}")
//    List<ProductDetailsDTO> getProductMerchantByMerchantId(@PathVariable("id") String merchantId);



    @PostMapping("/checkQuantity")
    public boolean checkQuantity(@RequestBody QuantityUpdateDTO quantityUpdateDTO);

    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestBody QuantityUpdateDTO quantityUpdateDTO);

//
//    @GetMapping("/getMerchantByProductId/{id}")
//    List<MerchantDTO> getMerchantByProductId(@PathVariable("id") String productId);
//
//    @GetMapping("/getDefaultMerchantByProductId/{id}")
//    ProductMerchantDTO getDefaultMerchantByProductId(@PathVariable("id") String productId);
}

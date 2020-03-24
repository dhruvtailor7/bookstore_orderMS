package com.coviam.team3bookstorebackend.orderMS.client;

import com.coviam.team3bookstorebackend.orderMS.dto.CartDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.CartListDTO;
import com.coviam.team3bookstorebackend.orderMS.dto.RemoveAllDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart")
public interface CartClient {
    @PostMapping("/add")
    ResponseEntity<String> add(@RequestBody CartDTO cartDTO);
//
//    @DeleteMapping("/remove")
//    ResponseEntity<String> remove(@RequestBody CartDTO cartDTO);

    @DeleteMapping("/removeAllFromCart")
    ResponseEntity<String> removeAll(@RequestBody RemoveAllDTO removeAllDTO);

    @GetMapping("/getFromCart/{id}")
    List<CartListDTO> getByCartId(@PathVariable("id") String cartId);
//
//    @PostMapping("/updateGuestCart")
//    void updateGuestCart(@RequestBody CartUpdateDTO cartUpdateDTO);
}

package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.CartDTO;
import com.web.webStoreApp.mainApi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody CartDTO dto) {
        String responseMessage = cartService.putProductInTheCart(dto);
        if (responseMessage.equals("Already existing product has been updated!")) {
            return ResponseEntity.ok(responseMessage);
        } else if (responseMessage.equals("A new type of product has been added to your cart!")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
    // curl -X POST "http://localhost:8080/api/main/cart/add" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2}"

    @PostMapping("/change")
    public ResponseEntity<String> change(@RequestBody CartDTO dto) {
        String responseMessage = cartService.changePosition(dto);
        if (responseMessage.equals("Position has been changed") || responseMessage.equals("Position has been deleted due to decreasing")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
    // curl -X POST "http://localhost:8080/api/main/cart/change" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2, \"amount\": 1}"

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePosition(@RequestBody CartDTO dto) {
        String responseMessage = cartService.deletePosition(dto);
        if (responseMessage.equals("Position has been deleted")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
    // curl -X DELETE "http://localhost:8080/api/main/cart/delete" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2}"

    @PostMapping("/buy")
    public ResponseEntity<String> buyPosition(@RequestBody CartDTO dto) {
        String responseMessage = cartService.buyPosition(dto);
        if (responseMessage.equals("Position has been bought") || responseMessage.equals("Part of products in the position have been bought")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
    // curl -X POST "http://localhost:8080/api/main/cart/buy" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2, \"amount\": 1}"

}

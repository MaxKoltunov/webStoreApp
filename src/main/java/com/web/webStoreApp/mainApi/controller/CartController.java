package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.CartDTO;
import com.web.webStoreApp.mainApi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/common/add")
    public void add(@RequestBody CartDTO dto) {
        cartService.putProductInTheCart(dto);
    }
    // curl -X POST "http://localhost:8080/api/main/cart/common/add" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2}"

    @PostMapping("/common/change")
    public void change(@RequestBody CartDTO dto) {
        cartService.changePosition(dto);
    }
    // curl -X POST "http://localhost:8080/api/main/cart/common/change" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2, \"amount\": 1}"

    @DeleteMapping("/common/delete")
    public void deletePosition(@RequestBody CartDTO dto) {
        cartService.deletePosition(dto);
    }
    // curl -X DELETE "http://localhost:8080/api/main/cart/common/delete" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2}"

    @PostMapping("/common/buy")
    public void buyPosition(@RequestBody CartDTO dto) {
        cartService.buyPosition(dto);
    }
    // curl -X POST "http://localhost:8080/api/main/cart/common/buy" -H "Content-Type: application/json" -d "{\"userId\": 1, \"productId\": 2, \"amount\": 1}"

}

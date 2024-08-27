package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.ProductDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.exceptions.ObjectNotFoundException;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import com.web.webStoreApp.mainApi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/main/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/admin/add")
    public void addProduct(@RequestBody ProductDTO dto) {
        productService.addProduct(dto);
    }
    // curl -X POST "http://localhost:8080/api/main/products/admin/add" -H "Content-Type: application/json" -d "{\"name\": \"test_product\", \"type\": \"test_type\", \"brand\":\"test_brand\", \"cost\": 100, \"arrival_date\": \"2024-08-01T06:00:00+05:00\", \"discount_id\": null}"

    @PostMapping("/admin/change")
    public void change(@RequestBody ProductDTO dto) {
        productService.changeAmount(dto);
    }
    // curl -X POST "http://localhost:8080/api/main/products/admin/change" -H "Content-Type: application/json" -d "{\"name\": \"bread\", \"type\": \"bakery\", \"brand\":\"smak\", \"amount\": 20}"

    @DeleteMapping("/admin/delete")
    public void deleteProduct(@RequestBody ProductDTO dto) {
        productService.deleteProduct(dto.getName(), dto.getType(), dto.getBrand());
    }
    // curl -X DELETE "http://localhost:8080/api/main/products/admin/delete" -H "Content-Type: application/json" -d "{\"name\":\"test_product\", \"type\":\"test_type\", \"brand\":\"test_brand\"}"

}

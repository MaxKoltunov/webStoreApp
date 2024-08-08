package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.ProductDTO;
import com.web.webStoreApp.mainApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductDTO dto) {
        productService.addProduct(dto);
        return "Product has been added or updated";
    }

    // curl -X POST "http://localhost:8080/api/main/products/add" -H "Content-Type: application/json" -d "{\"name\": \"test_product\", \"type\": \"test_type\", \"brand\":\"test_brand\", \"cost\": 100, \"arrival_date\": \"2024-08-01 06:00:00\", \"discount_id\": null}"

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestBody ProductDTO dto) {
        productService.deleteProduct(dto.getName(), dto.getType(), dto.getBrand());
        return "Product has been deleted";
    }
    // curl -X DELETE "http://localhost:8080/api/main/products/delete" -H "Content-Type: application/json" -d "{\"name\":\"test_product\", \"type\":\"test_type\", \"brand\":\"test_brand\"}"
}

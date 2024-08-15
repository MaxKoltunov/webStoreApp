package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.dto.ProductDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import com.web.webStoreApp.mainApi.service.ExistingDiscountService;
import com.web.webStoreApp.mainApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/main/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExsistingDiscountRepository exsistingDiscountRepository;

    @Autowired
    private ExistingDiscountService existingDiscountService;

    private static final ZoneId TIME_ZONE = ZoneId.of("UTC+05:00");

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductDTO dto) {
        productService.addProduct(dto);
        return "Product has been added or updated";
    }
    // curl -X POST "http://localhost:8080/api/main/products/add" -H "Content-Type: application/json" -d "{\"name\": \"test_product\", \"type\": \"test_type\", \"brand\":\"test_brand\", \"cost\": 100, \"arrival_date\": \"2024-08-01T06:00:00+05:00\", \"discount_id\": null}"

    @PostMapping("/change")
    public ResponseEntity<String> change(@RequestBody ProductDTO dto) {
        String responseMessage = productService.changeAmount(dto);
        if (responseMessage.equals("Product has been deleted due to decreasing") || responseMessage.equals("Amount has been changed")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
    // curl -X POST "http://localhost:8080/api/main/products/change" -H "Content-Type: application/json" -d "{\"name\": \"bread\", \"type\": \"bakery\", \"brand\":\"smak\", \"amount\": 20}"

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestBody ProductDTO dto) {
        productService.deleteProduct(dto.getName(), dto.getType(), dto.getBrand());
        return "Product has been deleted";
    }
    // curl -X DELETE "http://localhost:8080/api/main/products/delete" -H "Content-Type: application/json" -d "{\"name\":\"test_product\", \"type\":\"test_type\", \"brand\":\"test_brand\"}"

    @Transactional
    public String mapDiscountToProducts(ExistingDiscountDTO dto) {
        Optional<ExistingDiscount> discountOpt = exsistingDiscountRepository.findByAll(dto.getName(), dto.getType(), dto.getProductType(), dto.getStartDate(), dto.getEndDate());
        if (discountOpt.isEmpty()) {
            return "An error occurred: null pointer for discount";
        }
        ExistingDiscount discount = discountOpt.get();

        List<Product> productList = productRepository.findByType(dto.getProductType());
        if (productList.isEmpty()) {
            return "No suitable products for this discount were found";
        }
        for (Product product : productList) {
            product.setDiscount(discount);
            productRepository.save(product);
        }
        return "A new discount for product has been added";
    }

    public String checkDiscountsActualityInProducts() {
        List<Long> productIds = productRepository.getAllIds();
        if (productIds.isEmpty()) {
            return "There are no products";
        }
        for (Long id : productIds) {
            Optional<Product> productOpt = productRepository.findById(id);
            if (productOpt.isEmpty()) {
                return "Something went wrong: null pointer for product";
            }
            Product product = productOpt.get();

            Optional<ExistingDiscount> discountOpt = Optional.ofNullable(product.getDiscount());

            if (discountOpt.isPresent()) {
                ExistingDiscount discount = discountOpt.get();
                ZonedDateTime now = ZonedDateTime.now(TIME_ZONE);
                ZonedDateTime endDateTime = discount.getEndDate().toInstant().atZone(TIME_ZONE);
                if (now.isAfter(endDateTime)) {
                    product.setDiscount(null);
                    productRepository.save(product);
                    System.out.println("Ended discount has been deleted from product");
                }
            }
        }
        return "Discount actuality has been checked";
    }
}

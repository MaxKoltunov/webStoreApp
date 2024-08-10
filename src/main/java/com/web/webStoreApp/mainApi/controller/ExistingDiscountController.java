package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.service.ExistingDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main/discounts")
public class ExistingDiscountController {

    @Autowired
    public ExistingDiscountService existingDiscountService;

    @PostMapping("/add")
    public String addExistingDiscount(@RequestBody ExistingDiscountDTO dto) {
        existingDiscountService.addExistingDiscount(dto.getName(), dto.getType(), dto.getProductType(), dto.getStartDate(), dto.getEndDate());
        return "A new discount has been added";
    }
    // curl -X POST "http://localhost:8080/api/main/discounts/add" -H "Content-Type: application/json" -d "{\"name\": \"test_discount\", \"type\": \"test_type\", \"productType\": \"test_product_type\", \"startDate\": \"2024-08-01\", \"endDate\": \"2024-08-14\"}"

    @DeleteMapping("/delete")
    public String deleteExistingDiscount(@RequestBody ExistingDiscountDTO dto) {
        existingDiscountService.deleteExistingDiscount(dto.getName(), dto.getType(), dto.getProductType());
        return "Discount has been deleted";
    }
    // curl -X DELETE "http://localhost:8080/api/main/discounts/delete" -H "Content-Type: application/json" -d "{\"name\":\"test_discount\", \"type\":\"test_type\", \"productType\": \"test_product_type\"}"
}

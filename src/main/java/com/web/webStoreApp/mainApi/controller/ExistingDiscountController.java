package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import com.web.webStoreApp.mainApi.service.ExistingDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/main/discounts")
public class ExistingDiscountController {

    @Autowired
    public ExistingDiscountService existingDiscountService;

    @Autowired
    public ExsistingDiscountRepository exsistingDiscountRepository;


    @PostMapping("/admin/add")
    public void addExistingDiscount(@RequestBody ExistingDiscountDTO dto) {
        existingDiscountService.addExistingDiscount(dto);
    }
    // curl -X POST "http://localhost:8080/api/main/discounts/admin/add" -H "Content-Type: application/json" -d "{\"name\": \"test_discount\", \"type\": \"test_type\", \"productType\": \"test_product_type\", \"startDate\": \"2024-08-01T06:00:00+05:00\", \"endDate\": \"2024-08-14T06:00:00+05:00\"}"

    @DeleteMapping("/admin/delete")
    public void deleteExistingDiscount(@RequestBody ExistingDiscountDTO dto) {
        existingDiscountService.deleteExistingDiscount(dto.getName(), dto.getType(), dto.getProductType());
    }
    // curl -X DELETE "http://localhost:8080/api/main/discounts/admin/delete" -H "Content-Type: application/json" -d "{\"name\":\"test_discount\", \"type\":\"test_type\", \"productType\": \"test_product_type\"}"

}

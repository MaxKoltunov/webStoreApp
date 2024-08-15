package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import com.web.webStoreApp.mainApi.service.ExistingDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/main/discounts")
public class ExistingDiscountController {

    @Autowired
    private ProductController productController;

    @Autowired
    public ExistingDiscountService existingDiscountService;

    @Autowired
    public ExsistingDiscountRepository exsistingDiscountRepository;

    private static final ZoneId TIME_ZONE = ZoneId.of("UTC+05:00");

    @PostMapping("/add")
    public String addExistingDiscount(@RequestBody ExistingDiscountDTO dto) {
        existingDiscountService.addExistingDiscount(dto.getName(), dto.getType(), dto.getProductType(), dto.getStartDate(), dto.getEndDate());
        return "A new discount has been added";
    }
    // curl -X POST "http://localhost:8080/api/main/discounts/add" -H "Content-Type: application/json" -d "{\"name\": \"test_discount\", \"type\": \"test_type\", \"productType\": \"test_product_type\", \"startDate\": \"2024-08-01T06:00:00+05:00\", \"endDate\": \"2024-08-14T06:00:00+05:00\"}"

    @DeleteMapping("/delete")
    public String deleteExistingDiscount(@RequestBody ExistingDiscountDTO dto) {
        existingDiscountService.deleteExistingDiscount(dto.getName(), dto.getType(), dto.getProductType());
        return "Discount has been deleted";
    }
    // curl -X DELETE "http://localhost:8080/api/main/discounts/delete" -H "Content-Type: application/json" -d "{\"name\":\"test_discount\", \"type\":\"test_type\", \"productType\": \"test_product_type\"}"

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void checkActuality() {

        productController.checkDiscountsActualityInProducts();

        List<Long> discountsIds = exsistingDiscountRepository.getAllIds();
        List<Optional<ExistingDiscount>> discountsOpt = new ArrayList<>();
        for (Long id : discountsIds) {
            discountsOpt.add(exsistingDiscountRepository.findById(id));
        }
        List<ExistingDiscount> discounts = new ArrayList<>();
        for (Optional<ExistingDiscount> discountOpt : discountsOpt) {
            if (discountOpt.isPresent()) {
                discounts.add(discountOpt.get());
            } else {
                System.out.println("Program faced null discount");
            }
        }

        ZonedDateTime now = ZonedDateTime.now(TIME_ZONE);

        for (ExistingDiscount discount : discounts) {
            ZonedDateTime endDateTime = discount.getEndDate().toInstant().atZone(TIME_ZONE);
            if (now.isAfter(endDateTime)) {
                existingDiscountService.deleteExistingDiscount(discount.getName(), discount.getType(), discount.getProductType());
                System.out.println("Ended discount has been deleted");
            }
        }

        System.out.println("Discount actuality has been checked");
    }
}

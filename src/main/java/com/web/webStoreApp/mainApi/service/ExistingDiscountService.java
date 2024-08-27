package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.exceptions.ObjectNotFoundException;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
public class ExistingDiscountService {

    @Autowired
    private ProductRepository productRepository;

    private final ExsistingDiscountRepository exsistingDiscountRepository;

    private static final ZoneId TIME_ZONE = ZoneId.of("UTC+05:00");

    @Autowired
    public ExistingDiscountService(ExsistingDiscountRepository exsistingDiscountRepository) {
        this.exsistingDiscountRepository = exsistingDiscountRepository;
    }


    public ExistingDiscount addExistingDiscount(ExistingDiscountDTO dto) {
        ExistingDiscount existingDiscount = ExistingDiscount.builder()
                .name(dto.getName())
                .type(dto.getType())
                .productType(dto.getProductType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        log.info("A new discount has been added");
        return exsistingDiscountRepository.save(existingDiscount);
    }

    @Transactional
    public void deleteExistingDiscount(String name, String type, String product_type) {
        exsistingDiscountRepository.deleteExistingDiscount(name, type, product_type);
        log.info("Discount has been deleted");
    }

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void checkActuality() {
        log.info("checkActuality() - checkup started");
        List<ExistingDiscount> discounts = exsistingDiscountRepository.findAll();

        ZonedDateTime now = ZonedDateTime.now(TIME_ZONE);

        for (ExistingDiscount discount : discounts) {
            ZonedDateTime endDateTime = discount.getEndDate().toInstant().atZone(TIME_ZONE);
            if (now.isAfter(endDateTime)) {

                List<Product> products = productRepository.findByType(discount.getProductType());

                if (products.isEmpty()) {
                    throw new ObjectNotFoundException("There are no products for this discount");
                }

                for (Product product : products) {
                    product.setExistingDiscount(null);
                }

                deleteExistingDiscount(discount.getName(), discount.getType(), discount.getProductType());
                log.info("Ended discount has been deleted");
            }
        }

        log.info("Discount actuality has been checked");
    }

}

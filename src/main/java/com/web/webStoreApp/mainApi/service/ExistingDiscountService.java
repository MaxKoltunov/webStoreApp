package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ExistingDiscountService {

    private final ExsistingDiscountRepository exsistingDiscountRepository;

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

}

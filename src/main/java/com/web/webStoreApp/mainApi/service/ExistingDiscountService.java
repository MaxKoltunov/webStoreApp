package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;


@Service
public class ExistingDiscountService {

    private final ExsistingDiscountRepository exsistingDiscountRepository;

    @Autowired
    public ExistingDiscountService(ExsistingDiscountRepository exsistingDiscountRepository) {
        this.exsistingDiscountRepository = exsistingDiscountRepository;
    }


    public ExistingDiscount addExistingDiscount(ExistingDiscountDTO dto) {
        ExistingDiscount existingDiscount = new ExistingDiscount();
        existingDiscount.setName(dto.getName());
        existingDiscount.setType(dto.getType());
        existingDiscount.setProductType(dto.getProductType());
        existingDiscount.setStartDate(dto.getStartDate());
        existingDiscount.setEndDate(dto.getEndDate());
        return exsistingDiscountRepository.save(existingDiscount);
    }

    @Transactional
    public void deleteExistingDiscount(String name, String type, String product_type) {
        exsistingDiscountRepository.deleteExistingDiscount(name, type, product_type);
    }

}

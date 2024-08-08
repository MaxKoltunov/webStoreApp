package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ExistingDiscountService {

    private final ExsistingDiscountRepository exsistingDiscountRepository;

    @Autowired
    public ExistingDiscountService(ExsistingDiscountRepository exsistingDiscountRepository) {
        this.exsistingDiscountRepository = exsistingDiscountRepository;
    }

    public ExistingDiscount addExistingDiscount(String name, String type, Timestamp start_date, Timestamp end_date) {
        ExistingDiscount existingDiscount = new ExistingDiscount();
        existingDiscount.setName(name);
        existingDiscount.setType(type);
        existingDiscount.setStartDate(start_date);
        existingDiscount.setEndDate(end_date);
        return exsistingDiscountRepository.save(existingDiscount);
    }

    @Transactional
    public void deleteExistingDiscount(String name, String type) {
        exsistingDiscountRepository.deleteExistingDiscount(name, type);
    }
}

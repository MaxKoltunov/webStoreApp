package com.web.webStoreApp.mainApi.repository;

import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ExsistingDiscountRepository extends JpaRepository<ExistingDiscount, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mainschema.discounts WHERE name = :name AND type = :type AND product_type = :product_type", nativeQuery = true)
    void deleteExistingDiscount(String name, String type, String product_type);
}

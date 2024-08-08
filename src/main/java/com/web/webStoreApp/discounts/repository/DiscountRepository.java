package com.web.webStoreApp.discounts.repository;

import com.web.webStoreApp.discounts.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM discountschema.discounts WHERE  type = :type AND productType = :productType", nativeQuery = true)
    void deleteByType(String type, String productType);
}

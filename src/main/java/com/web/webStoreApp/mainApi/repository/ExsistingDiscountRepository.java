package com.web.webStoreApp.mainApi.repository;

import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface ExsistingDiscountRepository extends JpaRepository<ExistingDiscount, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mainschema.discounts WHERE name = :name AND type = :type AND product_type = :product_type", nativeQuery = true)
    void deleteExistingDiscount(String name, String type, String product_type);

    @Query(value = "SELECT id FROM mainschema.discounts", nativeQuery = true)
    List<Long> getAllIds();

    @Query(value = "SELECT * FROM mainschema.discounts WHERE id = :id", nativeQuery = true)
    Optional<ExistingDiscount> findById(Long id);

    @Query(value = "SELECT * FROM mainschema.discounts WHERE product_type = :type", nativeQuery = true)
    Optional<ExistingDiscount> findByProductType(String type);

    @Query(value = "SELECT * FROM mainschema.discounts WHERE name = :name AND type = :type AND product_type = :product_type AND start_date = :start_date AND end_date = :end_date", nativeQuery = true)
    Optional<ExistingDiscount> findByAll(String name, String type, String product_type, Timestamp start_date, Timestamp end_date);
}

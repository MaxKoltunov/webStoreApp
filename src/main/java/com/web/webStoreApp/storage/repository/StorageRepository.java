package com.web.webStoreApp.storage.repository;

import com.web.webStoreApp.storage.entity.StorageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StorageRepository extends JpaRepository<StorageProduct, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM storageschema.storage WHERE name = :name AND type = :type AND brand = :brand AND rec_cost = :recCost", nativeQuery = true)
    void deleteProduct(String name, String type, String brand, Long recCost);
}

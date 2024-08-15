package com.web.webStoreApp.mainApi.repository;

import com.web.webStoreApp.mainApi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mainschema.products WHERE name = :name AND type = :type AND brand = :brand", nativeQuery = true)
    void deleteProduct(String name, String type, String brand);

    @Query(value = "SELECT * FROM mainschema.products WHERE name = :name AND type = :type AND brand = :brand", nativeQuery = true)
    Optional<Product> findByNameTypeBrand(String name, String type, String brand);

    @Query(value = "SELECT * FROM mainschema.products WHERE id = :id", nativeQuery = true)
    Optional<Product> findById(Long id);

    @Query(value = "SELECT id FROM mainschema.products", nativeQuery = true)
    List<Long> getAllIds();

    @Query(value = "SELECT * FROM mainschema.products WHERE type = :product_type", nativeQuery = true)
    List<Product>findByType(String product_type);
}

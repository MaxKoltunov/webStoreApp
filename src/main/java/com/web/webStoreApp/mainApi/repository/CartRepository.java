package com.web.webStoreApp.mainApi.repository;

import com.web.webStoreApp.mainApi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM mainschema.cart WHERE user_id = :user_id AND product_id = :product_id", nativeQuery = true)
    Optional<Cart> findByKey(Long user_id, Long product_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mainschema.cart WHERE user_id = :user_id AND product_id = :product_id", nativeQuery = true)
    void deletePositionByKey(Long user_id, Long product_id);

}

package com.web.webStoreApp.mainApi.repository;

import com.web.webStoreApp.mainApi.entity.LevelsOfLoyalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LevelsofLoyaltyRepository extends JpaRepository<LevelsOfLoyalty, Long> {

    @Query(value = "SELECT * FROM mainschema.levelsofloyalty WHERE level_name = :level_name", nativeQuery = true)
    LevelsOfLoyalty findByName(String level_name);
}

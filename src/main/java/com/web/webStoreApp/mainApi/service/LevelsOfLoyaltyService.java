package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.entity.LevelsOfLoyalty;
import com.web.webStoreApp.mainApi.repository.LevelsofLoyaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelsOfLoyaltyService {

    @Autowired
    private LevelsofLoyaltyRepository levelsofLoyaltyRepository;

    public LevelsOfLoyalty findByName(String level_name) {
        return levelsofLoyaltyRepository.findByName(level_name);
    }
}

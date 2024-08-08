package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.UserDTO;
import com.web.webStoreApp.mainApi.entity.LevelsOfLoyalty;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LevelsOfLoyaltyService levelsOfLoyaltyService;

    public String addUser(UserDTO dto) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(dto.getPhone_number());
        LevelsOfLoyalty level = levelsOfLoyaltyService.findByName(dto.getLevel_name());

        if (userOpt.isPresent()) {
            return "User with this phone number already exists";
        } else {
            User user = new User();
            user.setName(dto.getName());
            user.setBirthDay(dto.getBirthDay());
            user.setPhone_number(dto.getPhone_number());
            user.setLevel_of_loyalty(level);
            user.setExisting(true);
            userRepository.save(user);
            return "A new user has been added";
        }
    }

    @Transactional
    public void deleteUser(UserDTO dto) {
        userRepository.deleteUser(dto.getPhone_number());
    }

    public String changeLevel(UserDTO dto) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(dto.getPhone_number());
        LevelsOfLoyalty level = levelsOfLoyaltyService.findByName(dto.getLevel_name());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setLevel_of_loyalty(level);
            userRepository.save(user);
            return "Level of loyalty has been changed";
        } else {
            return "There is already a user with this phone number";
        }
    }
}

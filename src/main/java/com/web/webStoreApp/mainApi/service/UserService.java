package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.UserDTO;
import com.web.webStoreApp.mainApi.entity.LevelsOfLoyalty;
import com.web.webStoreApp.mainApi.entity.Role;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.exceptions.UserAlreadyExistsExcpetion;
import com.web.webStoreApp.mainApi.exceptions.UserNotFoundException;
import com.web.webStoreApp.mainApi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User create(User user) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            if (existingUser.isExisting()) {
                throw new UserAlreadyExistsExcpetion("User already exists");
            } else {
                userRepository.deleteUser(user.getPhoneNumber());
            }
        }

        return userRepository.save(user);
    }


    @Transactional
    public void changeExistingInUser(UserDTO dto) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(dto.getPhoneNumber());
        if (userOpt.isPresent()) {
            userRepository.changeExistingInUser(dto.getPhoneNumber());
            log.info("User's existing was changed");
        } else {
            throw new UserNotFoundException("There is no user with this phone number");
        }
    }

    public void changeLevel(UserDTO dto) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(dto.getPhoneNumber());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setLevelOfLoyalty(LevelsOfLoyalty.valueOf(dto.getLevelName()));
            userRepository.save(user);
            log.info("Level of loyalty has been changed");
        } else {
            throw new UserNotFoundException("There is no user with this phone number");
        }
    }

    public User getByUsername(String username) {
        return userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);
    }
}

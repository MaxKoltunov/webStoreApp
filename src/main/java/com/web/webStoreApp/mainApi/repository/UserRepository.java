package com.web.webStoreApp.mainApi.repository;

import com.web.webStoreApp.mainApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE mainschema.users SET existing = false WHERE phone_number = :phoneNumber", nativeQuery = true)
    void changeExistingInUser(String phoneNumber);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mainschema.users WHERE phone_number = :phoneNumber", nativeQuery = true)
    void deleteUser(String phoneNumber);


    @Query(value = "SELECT * FROM mainschema.users WHERE phone_number = :phoneNumber", nativeQuery = true)
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM mainschema.users WHERE id = :id", nativeQuery = true)
    Optional<User> findById(Long id);

    @Query(value = "SELECT password FROM mainschema.users WHERE phone_number = :phoneNumber", nativeQuery = true)
    Optional<User> getPassword(String phoneNumber);
}

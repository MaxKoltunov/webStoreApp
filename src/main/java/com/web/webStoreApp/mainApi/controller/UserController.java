package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.UserDTO;
import com.web.webStoreApp.mainApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main/users")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@RequestBody UserDTO dto) {
        userService.changeExistingInUser(dto);
    }
    // curl -X DELETE "http://localhost:8080/api/main/users/admin/delete" -H "Content-Type: application/json" -d "{\"phoneNumber\": \"+79129215943\"}"


    @PostMapping("/admin/changelevel")
    @PreAuthorize("hasRole('ADMIN')")
    public void changeLevel(@RequestBody UserDTO dto) {
        userService.changeLevel(dto);
    }
    // curl -X POST "http://localhost:8080/api/main/users/admin/changelevel" -H "Content-Type: application/json" -d "{\"phoneNumber\": \"+79129215943\", \"level_name\": \"bronze\"}"

}

package com.web.webStoreApp.mainApi.controller;


import com.web.webStoreApp.mainApi.dto.UserDTO;
import com.web.webStoreApp.mainApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main/users")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/admin/delete")
    public String deleteUser(@RequestBody UserDTO dto) {
        userService.changeExistingInUser(dto);
        return "User has been deleted";
    }
    // curl -X DELETE "http://localhost:8080/api/main/users/delete" -H "Content-Type: application/json" -d "{\"phone_number\": \"+79129215943\"}"


    @PostMapping("/admin/changelevel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeLevel(@RequestBody UserDTO dto) {
        String responseMessage = userService.changeLevel(dto);
        if (responseMessage.equals("Level of loyalty has been changed")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
    // curl -X POST "http://localhost:8080/api/main/users/changelevel" -H "Content-Type: application/json" -d "{\"phone_number\": \"+79129215943\", \"level_name\": \"bronze\"}"


}

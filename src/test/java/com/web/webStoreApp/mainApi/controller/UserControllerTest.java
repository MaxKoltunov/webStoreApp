package com.web.webStoreApp.mainApi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webStoreApp.mainApi.dto.UserDTO;
import com.web.webStoreApp.mainApi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setPhoneNumber("+1234567890");

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteUser_ShouldReturnOk() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("+1234567890");

        mockMvc.perform(delete("/api/main/users/admin/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteUserThatNotExists_ShouldReturnNotFound() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("wrong phone number");

        mockMvc.perform(delete("/api/main/users/admin/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteUser_ShouldReturnForbidden() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("+1234567890");

        mockMvc.perform(delete("/api/main/users/admin/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void changeLevel_ShouldReturnOk() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("+1234567890");
        userDTO.setLevelName("silver");

        mockMvc.perform(post("/api/main/users/admin/changelevel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void changeLevel_ShouldReturnForbidden() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("+1234567890");
        userDTO.setLevelName("silver");

        mockMvc.perform(post("/api/main/users/admin/changelevel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void changeLevel_ShouldReturnNotFound() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("wrong phone number");
        userDTO.setLevelName("silver");

        mockMvc.perform(post("/api/main/users/admin/changelevel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());
    }
}

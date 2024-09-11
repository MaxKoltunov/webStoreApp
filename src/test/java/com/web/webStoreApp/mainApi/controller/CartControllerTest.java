package com.web.webStoreApp.mainApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webStoreApp.mainApi.dto.CartDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(CustomOrderer.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CartDTO cartDTO;

    @BeforeEach
    public void setUp() {
        cartDTO = CartDTO.builder()
                .userId(19L)
                .productId(20L)
                .amount(10L)
                .build();
    }

    @Test
    @WithMockUser
    @Order(1)
    public void testAdd_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/api/main/cart/common/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Order(2)
    public void testChange_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/api/main/cart/common/change")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cartDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Order(3)
    public void testBuy_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/api/main/cart/common/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartDTO)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    @Order(4)
    public void testDelete_ShouldReturnOk() throws Exception {

        mockMvc.perform(delete("/api/main/cart/common/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartDTO)))
                .andExpect(status().isOk());
    }
}


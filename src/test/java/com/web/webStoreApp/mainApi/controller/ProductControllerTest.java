package com.web.webStoreApp.mainApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webStoreApp.mainApi.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        productDTO = ProductDTO.builder()
                .name("testName")
                .type("testType")
                .brand("testBrand")
                .cost(100L)
                .arrivalDate(Timestamp.valueOf("2024-08-29 06:00:00"))
                .amount(10L)
                .discountId(null)
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Order(1)
    public void testAddProduct_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/api/main/products/admin/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Order(2)
    public void testAddProduct_ShouldReturnForbidden() throws Exception {

        mockMvc.perform(post("/api/main/products/admin/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Order(3)
    public void changeProductAmount_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/api/main/products/admin/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Order(4)
    public void changeProductAmount_ShouldReturnForbidden() throws Exception {

        mockMvc.perform(post("/api/main/products/admin/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Order(6)
    public void deleteProduct_ShouldReturnOk() throws Exception {

        mockMvc.perform(delete("/api/main/products/admin/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Order(5)
    public void deleteProduct_ShouldReturnForbidden() throws Exception {

        mockMvc.perform(delete("/api/main/products/admin/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isForbidden());
    }
}

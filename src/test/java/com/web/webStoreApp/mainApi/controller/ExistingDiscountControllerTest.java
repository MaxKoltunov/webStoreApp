package com.web.webStoreApp.mainApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(CustomOrderer.class)
public class ExistingDiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ExistingDiscountDTO discountDTO;

    @BeforeEach
    public void setUp() {
        discountDTO = ExistingDiscountDTO.builder()
                .name("testDiscount")
                .type("testType")
                .productType("testProductType")
                .startDate(Timestamp.valueOf("2024-08-29 06:00:00"))
                .endDate(Timestamp.valueOf("2024-08-29 12:00:00"))
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser(roles = "ADMIN")
    public void addExistingDiscount_ShouldReturnOk() throws Exception {

        mockMvc.perform(post("/api/main/discounts/admin/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discountDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @WithMockUser(roles = "USER")
    public void addExistingDiscount_ShouldReturnForbidden() throws Exception {

        mockMvc.perform(post("/api/main/discounts/admin/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discountDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    @WithMockUser(roles = "ADMIN")
    public void deleteExistingDiscount_ShouldReturnOk() throws Exception {

        mockMvc.perform(delete("/api/main/discounts/admin/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discountDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @WithMockUser(roles = "USER")
    public void deleteExistingDiscount_ShouldReturnForbidden() throws Exception {

        mockMvc.perform(delete("/api/main/discounts/admin/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discountDTO)))
                .andExpect(status().isForbidden());
    }
}

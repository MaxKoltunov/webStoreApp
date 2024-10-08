package com.web.webStoreApp.mainApi.service;

import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.exceptions.ObjectNotFoundException;
import com.web.webStoreApp.mainApi.repository.ExistingDiscountRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ExistingDiscountServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ExistingDiscountRepository existingDiscountRepository;

    @InjectMocks
    private ExistingDiscountService existingDiscountService;

    private static final ZoneId TIME_ZONE = ZoneId.of("UTC+05:00");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        existingDiscountRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testAddExistingDiscount() {
        // Given
        ExistingDiscountDTO dto = new ExistingDiscountDTO();
        dto.setName("TestDiscount");
        dto.setType("TestType");
        dto.setProductType("TestProductType");
        dto.setStartDate(Timestamp.from(ZonedDateTime.now(TIME_ZONE).toInstant()));
        dto.setEndDate(Timestamp.from(ZonedDateTime.now(TIME_ZONE).plusMinutes(3).toInstant()));

        ExistingDiscount discount = ExistingDiscount.builder()
                .name(dto.getName())
                .type(dto.getType())
                .productType(dto.getProductType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        when(existingDiscountRepository.save(any(ExistingDiscount.class)))
                .thenReturn(discount);

        // When
        ExistingDiscount result = existingDiscountService.addExistingDiscount(dto);

        // Then
        verify(existingDiscountRepository, times(1)).save(any(ExistingDiscount.class));
        assertThat(result.getName()).isEqualTo(dto.getName());
        assertThat(result.getType()).isEqualTo(dto.getType());
        assertThat(result.getProductType()).isEqualTo(dto.getProductType());
    }

    @Test
    void testDeleteExistingDiscount() {
        // When
        existingDiscountService.deleteExistingDiscount("TestDiscount", "TestType", "TestProductType");

        // Then
        verify(existingDiscountRepository, times(1)).deleteExistingDiscount(any(), any(), any());
    }

    @Test
    void testCheckActuality_DiscountNotExpired() {
        // Given
        ExistingDiscount discount = new ExistingDiscount();
        discount.setName("TestDiscount");
        discount.setType("TestType");
        discount.setProductType("TestProductType");
        discount.setEndDate(Timestamp.from(ZonedDateTime.now(TIME_ZONE).plusMinutes(3).toInstant()));

        when(existingDiscountRepository.findAll())
                .thenReturn(List.of(discount));

        // When
        existingDiscountService.checkActuality();

        // Then
        verify(productRepository, times(0)).findByType(any());
        verify(existingDiscountRepository, times(0)).deleteExistingDiscount(any(), any(), any());
    }

    @Test
    void testCheckActuality_DiscountExpired() {
        // Given
        ExistingDiscount discount = new ExistingDiscount();
        discount.setName("TestDiscount");
        discount.setType("TestType");
        discount.setProductType("TestProductType");
        discount.setEndDate(Timestamp.from(ZonedDateTime.now(TIME_ZONE).minusMinutes(3).toInstant()));

        when(existingDiscountRepository.findAll())
                .thenReturn(List.of(discount));

        Product product = new Product();
        when(productRepository.findByType(discount.getProductType()))
                .thenReturn(List.of(product));

        // When
        existingDiscountService.checkActuality();

        // Then
        verify(productRepository, times(1)).findByType(discount.getProductType());
        verify(existingDiscountRepository, times(1))
                .deleteExistingDiscount(discount.getName(), discount.getType(), discount.getProductType());
    }

    @Test
    void testCheckActuality_NoProductsFound() {
        // Given
        ExistingDiscount discount = new ExistingDiscount();
        discount.setName("TestDiscount");
        discount.setType("TestType");
        discount.setProductType("TestProductType");
        discount.setEndDate(Timestamp.from(ZonedDateTime.now(TIME_ZONE).minusMinutes(3).toInstant()));

        when(existingDiscountRepository.findAll())
                .thenReturn(List.of(discount));

        when(productRepository.findByType(discount.getProductType()))
                .thenReturn(List.of());

        // When
        assertThrows(ObjectNotFoundException.class, () -> existingDiscountService.checkActuality());

        // Then
        verify(productRepository, times(1)).findByType(discount.getProductType());
    }
}
package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.ProductDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.exceptions.ObjectNotFoundException;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAddProduct_ProductExists() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("ExistingProduct");
        productDTO.setType("ExistingType");
        productDTO.setBrand("ExistingBrand");
        productDTO.setArrivalDate(Timestamp.valueOf("2024-08-28 06:00:00"));

        Product existingProduct = new Product();
        existingProduct.setAmount(10L);

        when(productRepository.findByNameTypeBrand(any(), any(), any()))
                .thenReturn(Optional.of(existingProduct));

        // When
        productService.addProduct(productDTO);

        // Then
        verify(productRepository, times(1)).save(existingProduct);
        verify(productRepository, times(1)).findByNameTypeBrand(any(), any(), any());
    }

    @Test
    void testAddProduct_NewProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("NewProduct");
        productDTO.setType("NewType");
        productDTO.setBrand("NewBrand");
        productDTO.setCost(100L);
        productDTO.setArrivalDate(Timestamp.valueOf("2024-08-28 06:00:00"));
        productDTO.setAmount(5L);

        when(productRepository.findByNameTypeBrand(any(), any(), any()))
                .thenReturn(Optional.empty());

        // When
        productService.addProduct(productDTO);

        // Then
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findByNameTypeBrand(any(), any(), any());
    }

    @Test
    void testChangeAmount_ProductExists() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("ExistingProduct");
        productDTO.setType("ExistingType");
        productDTO.setBrand("ExistingBrand");
        productDTO.setAmount(-5L);

        Product existingProduct = new Product();
        existingProduct.setAmount(10L);

        when(productRepository.findByNameTypeBrand(any(), any(), any()))
                .thenReturn(Optional.of(existingProduct));

        // When
        productService.changeAmount(productDTO);

        // Then
        verify(productRepository, times(1)).save(existingProduct);
        verify(productRepository, times(1)).findByNameTypeBrand(any(), any(), any());
    }

    @Test
    void testChangeAmount_ProductDoesNotExist_throwObjectNotFoundException() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("NotExistingProduct");
        productDTO.setType("NotExistingType");
        productDTO.setBrand("NotExistingBrand");
        productDTO.setAmount(-5L);

        when(productRepository.findByNameTypeBrand(any(), any(), any()))
                .thenReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> productService.changeAmount(productDTO));

        // Then
        verify(productRepository, times(1)).findByNameTypeBrand(any(), any(), any());
    }

    @Test
    void testDeleteProduct() {
        // When
        productService.deleteProduct("TestProduct", "TestType", "TestBrand");

        // Then
        verify(productRepository, times(1)).deleteProduct(any(), any(), any());
    }

    @Test
    void testMapDiscountToProducts_DiscountExists() {
        // Given
        ExistingDiscount discount = new ExistingDiscount();
        discount.setProductType("Type1");

        Product product = new Product();

        when(productRepository.findByType(any())).thenReturn(List.of(product));

        // When
        productService.mapDiscountToProducts(discount);

        // Then
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testMapDiscountToProducts_NoProductsFound_throwObjectNotFoundException() {
        // Given
        ExistingDiscount discount = new ExistingDiscount();
        discount.setProductType("NotExistingType");

        when(productRepository.findByType(any())).thenReturn(List.of());

        // When
        assertThrows(ObjectNotFoundException.class, () -> productService.mapDiscountToProducts(discount));

        // Then
        verify(productRepository, times(1)).findByType(any());
    }
}


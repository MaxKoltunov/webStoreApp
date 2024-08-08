package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.ProductDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.repository.ExsistingDiscountRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExsistingDiscountRepository exsistingDiscountRepository;

    public void addProduct(ProductDTO dto) {
        Optional<Product> productOpt = productRepository.findByNameTypeBrand(dto.getName(), dto.getType(), dto.getBrand());

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setArrivalDate(dto.getArrival_date());
            product.setAmount(product.getAmount() + 1);
            productRepository.save(product);
        } else {
            Product product = new Product();
            product.setName(dto.getName());
            product.setType(dto.getType());
            product.setCost(dto.getCost());
            product.setBrand(dto.getBrand());
            product.setArrivalDate(dto.getArrival_date());
            product.setAmount(1L);

            if (dto.getDiscountId() != null) {
                ExistingDiscount discount = exsistingDiscountRepository.findById(dto.getDiscountId()).orElse(null);
                product.setDiscount(discount);
            }

            productRepository.save(product);
        }
    }

    @Transactional
    public void deleteProduct(String name, String type, String brand) {
        productRepository.deleteProduct(name, type, brand);
    }
}

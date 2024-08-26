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
            product.setArrivalDate(dto.getArrivalDate());
            product.setAmount(product.getAmount() + 1);
            productRepository.save(product);
        } else {
            Product product = Product.builder()
                    .name(dto.getName())
                    .type(dto.getType())
                    .brand(dto.getBrand())
                    .cost(dto.getCost())
                    .arrivalDate(dto.getArrivalDate())
                    .build();
            if (dto.getAmount() == null) {
                product.setAmount(1L);
            } else {
                product.setAmount(dto.getAmount());
            }
            if (dto.getDiscountId() != null) {
                ExistingDiscount discount = exsistingDiscountRepository.findById(dto.getDiscountId()).orElse(null);
                product.setExistingDiscount(discount);
            }

            productRepository.save(product);
        }
    }

    public String changeAmount(ProductDTO dto) {
        Optional<Product> productOpt = productRepository.findByNameTypeBrand(dto.getName(), dto.getType(), dto.getBrand());

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setAmount(product.getAmount() + dto.getAmount());
            if (product.getAmount() <= 0) {
                productRepository.deleteProduct(dto.getName(), dto.getType(), dto.getBrand());
                return "Product has been deleted due to decreasing";
            }
            productRepository.save(product);
            return "Amount has been changed";
        } else {
            return "There is no such product";
        }
    }

    @Transactional
    public void deleteProduct(String name, String type, String brand) {
        productRepository.deleteProduct(name, type, brand);
    }
}

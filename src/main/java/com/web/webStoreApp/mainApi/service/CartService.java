package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.CartDTO;
import com.web.webStoreApp.mainApi.entity.Cart;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.exceptions.CartAmountException;
import com.web.webStoreApp.mainApi.exceptions.CartCreatingException;
import com.web.webStoreApp.mainApi.exceptions.CartNotFoundException;
import com.web.webStoreApp.mainApi.repository.CartRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import com.web.webStoreApp.mainApi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public void putProductInTheCart(CartDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Product> productOpt = productRepository.findById(dto.getProductId());
        if (userOpt.isPresent() && productOpt.isPresent()) {
            Optional<Cart> cartOpt = cartRepository.findByKey(dto.getUserId(), dto.getProductId());
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                cart.setAmount(dto.getAmount() + 1);
                cartRepository.save(cart);
                log.info("Already existing product has been updated!");
            } else {
                Cart cart = Cart.builder()
                        .userId(dto.getUserId())
                        .productId(dto.getProductId())
                        .amount(1L)
                        .build();
                cartRepository.save(cart);
                log.info("A new type of product has been added to your cart!");
            }
        } else {
            throw new CartCreatingException("There is no user or product with this id");
        }
    }

    public void deletePosition(CartDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Product> productOpt = productRepository.findById(dto.getProductId());
        if (userOpt.isPresent() && productOpt.isPresent()) {
            cartRepository.deletePositionByKey(dto.getUserId(), dto.getProductId());
            log.info("Position has been deleted");
        } else {
            throw new CartNotFoundException("There is no position with this id");
        }
    }

    public void buyPosition(CartDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Product> productOpt = productRepository.findById(dto.getProductId());
        if (userOpt.isPresent() && productOpt.isPresent()) {
            Optional<Cart> cartOpt = cartRepository.findByKey(dto.getUserId(), dto.getProductId());
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                Product product = productOpt.get();
                if (cart.getAmount() == dto.getAmount()) {
                    cartRepository.deletePositionByKey(dto.getUserId(), dto.getProductId());
                    product.setAmount(product.getAmount() - dto.getAmount());
                    productRepository.save(product);
                    log.info("Position has been bought");
                } else if (cart.getAmount() > dto.getAmount()) {
                    cart.setAmount(cart.getAmount() - dto.getAmount());
                    cartRepository.save(cart);
                    product.setAmount(product.getAmount() - dto.getAmount());
                    log.info("Part of products in the position have been bought");
                } else {
                    throw new CartAmountException("There are no that much products in the position");
                }
            } else {
                throw new CartNotFoundException("There is no position with this id");
            }
        } else {
            throw new CartCreatingException("There is no user or product with this id");
        }
    }

    public void changePosition(CartDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Product> productOpt = productRepository.findById(dto.getProductId());
        if (userOpt.isPresent() && productOpt.isPresent()) {
            Optional<Cart> cartOpt = cartRepository.findByKey(dto.getUserId(), dto.getProductId());
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                cart.setAmount(cart.getAmount() + dto.getAmount());
                if (cart.getAmount() <= 0) {
                    cartRepository.deletePositionByKey(dto.getUserId(), dto.getProductId());
                    log.info("Position has been deleted due to decreasing");
                }
                cartRepository.save(cart);
                log.info("Position has been changed");
            } else {
                throw new CartNotFoundException("There is no position with this id");
            }
        } else {
            throw new CartCreatingException("There is no user or product with this id");
        }
    }
}

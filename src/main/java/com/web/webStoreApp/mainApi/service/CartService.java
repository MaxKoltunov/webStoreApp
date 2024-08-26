package com.web.webStoreApp.mainApi.service;


import com.web.webStoreApp.mainApi.dto.CartDTO;
import com.web.webStoreApp.mainApi.entity.Cart;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.repository.CartRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import com.web.webStoreApp.mainApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public String putProductInTheCart(CartDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Product> productOpt = productRepository.findById(dto.getProductId());
        if (userOpt.isPresent() && productOpt.isPresent()) {
            Optional<Cart> cartOpt = cartRepository.findByKey(dto.getUserId(), dto.getProductId());
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                cart.setAmount(dto.getAmount() + 1);
                cartRepository.save(cart);
                return "Already existing product has been updated!";
            } else {
                Cart cart = Cart.builder()
                        .userId(dto.getUserId())
                        .productId(dto.getProductId())
                        .amount(1L)
                        .build();
                cartRepository.save(cart);
                return "A new type of product has been added to your cart!";
            }
        } else {
            return "There is no user or product with this id";
        }
    }

    public String deletePosition(CartDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Product> productOpt = productRepository.findById(dto.getProductId());
        if (userOpt.isPresent() && productOpt.isPresent()) {
            cartRepository.deletePositionByKey(dto.getUserId(), dto.getProductId());
            return "Position has been deleted";
        } else {
            return "There is no position with this id";
        }
    }

    public String buyPosition(CartDTO dto) {
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
                    return "Position has been bought";
                } else if (cart.getAmount() > dto.getAmount()) {
                    cart.setAmount(cart.getAmount() - dto.getAmount());
                    cartRepository.save(cart);
                    product.setAmount(product.getAmount() - dto.getAmount());
                    return "Part of products in the position have been bought";
                } else {
                    return "There are no that much products in the position";
                }
            } else {
                return "There is no position with this id";
            }
        } else {
            return "There is no user or product with this id";
        }
    }

    public String changePosition(CartDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Product> productOpt = productRepository.findById(dto.getProductId());
        if (userOpt.isPresent() && productOpt.isPresent()) {
            Optional<Cart> cartOpt = cartRepository.findByKey(dto.getUserId(), dto.getProductId());
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                cart.setAmount(cart.getAmount() + dto.getAmount());
                if (cart.getAmount() <= 0) {
                    cartRepository.deletePositionByKey(dto.getUserId(), dto.getProductId());
                    return "Position has been deleted due to decreasing";
                }
                cartRepository.save(cart);
                return "Position has been changed";
            } else {
                return "There is no position with this id";
            }
        } else {
            return "There is no user or product with this id";
        }
    }
}

package com.web.webStoreApp.mainApi.service;

import com.web.webStoreApp.mainApi.dto.CartDTO;
import com.web.webStoreApp.mainApi.entity.Cart;
import com.web.webStoreApp.mainApi.entity.Product;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.exceptions.CartAmountException;
import com.web.webStoreApp.mainApi.exceptions.CartCreatingException;
import com.web.webStoreApp.mainApi.exceptions.ObjectNotFoundException;
import com.web.webStoreApp.mainApi.repository.CartRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import com.web.webStoreApp.mainApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPutProductInTheCart_ExistingProduct_shouldSaveProduct() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();
        Cart cart = new Cart(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.of(cart));

        // When
        cartService.putProductInTheCart(cartDTO);

        // Then
        verify(cartRepository, times(1)).save(cart);
        assertEquals(2L, cart.getAmount());
    }

    @Test
    public void testPutProductInTheCart_NewProduct_shouldSaveProduct() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.empty());

        // When
        cartService.putProductInTheCart(cartDTO);

        // Then
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    public void testPutProductInTheCart_UserOrProductNotExist_throwsCartCreatingException() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.empty());
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.empty());


        // When + Then
        assertThrows(CartCreatingException.class, () -> cartService.putProductInTheCart(cartDTO));
    }

    @Test
    public void testDeletePosition_UserAndProductArePresent_shouldDeleteProduct() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));

        // When
        cartService.deletePosition(cartDTO);

        // Then
        verify(cartRepository, times(1)).deletePositionByKey(cartDTO.getUserId(), cartDTO.getProductId());

    }

    @Test
    public void testDeletePosition_UserAndProductAreEmpty_throwsObjectNotFoundException() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.empty());
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.empty());

        // When + Then
        assertThrows(ObjectNotFoundException.class, () -> cartService.deletePosition(cartDTO));
    }

    @Test
    public void testBuyPosition_UserAndProductArePresentAndCartAmountEqualsDTOAmount_AllHasBeenBought() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();
        product.setAmount(2L);

        Cart cart = new Cart(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.of(cart));

        // When
        cartService.buyPosition(cartDTO);

        // Then
        verify(cartRepository, times(1)).deletePositionByKey(cartDTO.getUserId(), cartDTO.getProductId());
        assertEquals(1L, product.getAmount());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testBuyPosition_UserAndProductArePresentAndCartAmountBiggerDTOAmount_PartHasBeenBought() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();
        product.setAmount(3L);

        Cart cart = new Cart(1L, 1L, 2L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.of(cart));

        // When
        cartService.buyPosition(cartDTO);

        // Then
        verify(cartRepository, times(1)).save(cart);
        assertEquals(2L, product.getAmount());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testBuyPosition_UserAndProductArePresentAndCartAmountLessDTOAmount_throwsCartAmountException() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 2L);

        User user = new User();
        Product product = new Product();
        product.setAmount(3L);

        Cart cart = new Cart(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.of(cart));

        // When + Then
        assertThrows(CartAmountException.class, () -> cartService.buyPosition(cartDTO));
    }

    @Test
    public void testBuyPosition_UserAndProductArePresentAndCartIsEmpty_throwsObjectNotFoundException() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.empty());

        // When + Then
        assertThrows(ObjectNotFoundException.class, () -> cartService.buyPosition(cartDTO));
    }

    @Test
    public void testBuyPosition_UserAndProductArePresentAndCartIsEmpty_throwsCartCreatingException() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.empty());
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.empty());

        // When + Then
        assertThrows(CartCreatingException.class, () -> cartService.buyPosition(cartDTO));
    }

    @Test
    public void testChangePosition_UserAndProductAndCartArePresent() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();

        Cart cart = new Cart(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.of(cart));

        // When
        cartService.changePosition(cartDTO);

        // Then
        assertEquals(2L, cart.getAmount());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testChangePosition_UserAndProductAndCartArePresentAndCartAmountDecreasedToZero_positionDeletedDueDecreasing() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, -2L);

        User user = new User();
        Product product = new Product();

        Cart cart = new Cart(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.of(cart));

        // When
        cartService.changePosition(cartDTO);

        // Then
        assert cart.getAmount() < 0;
        verify(cartRepository, times(1)).deletePositionByKey(cartDTO.getUserId(), cartDTO.getProductId());
    }

    @Test
    public void testChangePosition_UserAndProductArePresentAndCartIsEmpty_throwsObjectNotFoundException() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        User user = new User();
        Product product = new Product();

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findByKey(cartDTO.getUserId(), cartDTO.getProductId())).thenReturn(Optional.empty());

        // When + Then
        assertThrows(ObjectNotFoundException.class, () -> cartService.buyPosition(cartDTO));
    }

    @Test
    public void testChangePosition_UserAndProductAreEmpty_throwsCartCreatingException() {
        // Given
        CartDTO cartDTO = new CartDTO(1L, 1L, 1L);

        when(userRepository.findById(cartDTO.getUserId())).thenReturn(Optional.empty());
        when(productRepository.findById(cartDTO.getProductId())).thenReturn(Optional.empty());

        // When + Then
        assertThrows(CartCreatingException.class, () -> cartService.buyPosition(cartDTO));
    }
}

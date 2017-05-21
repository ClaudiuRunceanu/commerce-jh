package com.commerce.service;

import com.commerce.domain.Cart;
import com.commerce.repository.CartRepository;
import com.commerce.service.dto.CartDto;
import com.commerce.service.mapper.CartConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for cart.
 */
@Service
@Transactional
public class CartService {
    private CartRepository cartRepository;
    private CartConverter cartConverter;

    public CartService(CartRepository cartRepository, CartConverter cartConverter) {
        this.cartRepository = cartRepository;
        this.cartConverter = cartConverter;
    }

    public CartDto createNewCart(CartDto cartDto) {
        Cart cart = cartRepository.save(cartConverter.getCartModel(cartDto));
        cartDto.setId(cart.getId());
        return cartDto;
    }

    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return cartConverter.getCartDataList(carts);
    }

    public CartDto updateCart(CartDto cartDto) {
        cartRepository.save(cartConverter.getCartModel(cartDto));
        return cartDto;
    }

    public CartDto findById(Long id) {
        return cartConverter.getCartData(cartRepository.findOne(id));
    }

    public void deleteCart(Long id) {
        cartRepository.delete(id);
    }
}

package com.alten.testTech.service;

import com.alten.testTech.entity.AppUser;
import com.alten.testTech.entity.Cart;
import com.alten.testTech.payload.CreateUserRequest;

public interface CartService {
    public Cart getCartByUserId(Long userId);
    public Cart addProductToCart(Long userId, Long productId);
    public Cart removeProductFromCart(Long userId, Long productId);
    public Cart clearCart(Long userId);

}

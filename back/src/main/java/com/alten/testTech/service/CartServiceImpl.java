package com.alten.testTech.service;

import com.alten.testTech.entity.AppUser;
import com.alten.testTech.entity.Cart;
import com.alten.testTech.entity.Product;
import com.alten.testTech.repository.AppUserRepository;
import com.alten.testTech.repository.CartRepository;
import com.alten.testTech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Cart getCartByUserId(Long userId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow();
        return user.getCart();
    }

    @Override
    public Cart addProductToCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId).orElseThrow();
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.getProducts().removeIf(p -> p.getId().equals(productId));
        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getProducts().clear();
        return cartRepository.save(cart);
    }

}

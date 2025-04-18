package com.alten.testTech.controller;

import com.alten.testTech.entity.Cart;
import com.alten.testTech.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<Cart> addProduct(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, productId));
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Cart> removeProduct(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, productId));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
}

package com.alten.testTech.controller;

import com.alten.testTech.entity.Wishlist;
import com.alten.testTech.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<Wishlist> addProduct(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(userId, productId));
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Wishlist> removeProduct(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.removeProductFromWishlist(userId, productId));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Wishlist> clear(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.clearWishlist(userId));
    }
}

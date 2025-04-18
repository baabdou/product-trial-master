package com.alten.testTech.service;

import com.alten.testTech.entity.Wishlist;

public interface WishlistService {
    public Wishlist getWishlistByUserId(Long userId);
    public Wishlist addProductToWishlist(Long userId, Long productId);
    public Wishlist removeProductFromWishlist(Long userId, Long productId);
    public Wishlist clearWishlist(Long userId);

}

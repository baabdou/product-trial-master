package com.alten.testTech.service;

import com.alten.testTech.entity.AppUser;
import com.alten.testTech.entity.Product;
import com.alten.testTech.entity.Wishlist;
import com.alten.testTech.repository.AppUserRepository;
import com.alten.testTech.repository.ProductRepository;
import com.alten.testTech.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Wishlist getWishlistByUserId(Long userId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow();
        return user.getWishlist();
    }

    @Override
    public Wishlist addProductToWishlist(Long userId, Long productId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        Product product = productRepository.findById(productId).orElseThrow();
        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);
        }
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist removeProductFromWishlist(Long userId, Long productId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.getProducts().removeIf(p -> p.getId().equals(productId));
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist clearWishlist(Long userId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.getProducts().clear();
        return wishlistRepository.save(wishlist);
    }
}

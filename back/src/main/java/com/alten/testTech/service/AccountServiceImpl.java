package com.alten.testTech.service;

import com.alten.testTech.entity.AppUser;
import com.alten.testTech.entity.Cart;
import com.alten.testTech.payload.CreateUserRequest;
import com.alten.testTech.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${admin.email}")
    private String admin;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public AppUser createUser(CreateUserRequest request) {
        AppUser user = appUserRepository.findByEmail(request.getEmail());
        if(user != null) throw new RuntimeException("User already exists");
        if(!request.getPassword().equals(request.getConfirmedPassword())) throw new RuntimeException("please confirm password");
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        appUser.setEmail(request.getEmail());
        appUser.setFirstname(request.getFirstname());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        initUserCart(appUser);
        appUserRepository.save(appUser);
        return appUser;
    }

    @Override
    public AppUser loadUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public boolean isAdmin() {
        return getCurrentUser().equals(admin);
    }

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private void initUserCart(AppUser user) {
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
    }
}

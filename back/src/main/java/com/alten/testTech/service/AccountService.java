package com.alten.testTech.service;

import com.alten.testTech.entity.AppUser;
import com.alten.testTech.payload.CreateUserRequest;

public interface AccountService {
    public AppUser createUser(CreateUserRequest request);
    public AppUser loadUserByEmail(String email);
    public boolean isAdmin();

}

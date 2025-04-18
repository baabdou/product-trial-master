package com.alten.testTech.security;

import com.alten.testTech.entity.AppUser;
import com.alten.testTech.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser=accountService.loadUserByEmail(email);
        if(appUser==null) throw new UsernameNotFoundException("Invalid User");

        return new User(appUser.getUsername(),appUser.getPassword(), Collections.emptyList());
    }
}

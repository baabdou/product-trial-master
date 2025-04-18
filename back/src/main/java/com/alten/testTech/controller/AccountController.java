package com.alten.testTech.controller;

import com.alten.testTech.entity.AppUser;
import com.alten.testTech.payload.CreateUserRequest;
import com.alten.testTech.payload.LoginErrorResponse;
import com.alten.testTech.payload.LoginRequest;
import com.alten.testTech.security.JWTAuthenticationFilter;
import com.alten.testTech.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/account")
    public ResponseEntity<AppUser> save(@RequestBody CreateUserRequest request) {
        AppUser response = accountService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginReq)  {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String token = jwtAuthenticationFilter.successfulAuthentication(loginReq.getEmail());
            LinkedMultiValueMap<String, String> response = new LinkedMultiValueMap<>();

            response.add("token", token);
            return ResponseEntity.ok(response);

        }catch (BadCredentialsException e){
            LoginErrorResponse errorResponse = new LoginErrorResponse(HttpStatus.BAD_REQUEST,"Invalid email or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            LoginErrorResponse errorResponse = new LoginErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}

package com.alten.testTech.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    public String successfulAuthentication(String email) {

         return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis()+SecurityParams.EXPIRATION))
                .sign(Algorithm.HMAC256(SecurityParams.SECRET));
    }

}

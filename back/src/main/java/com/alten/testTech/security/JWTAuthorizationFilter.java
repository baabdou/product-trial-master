package com.alten.testTech.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
        response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        response.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,PATCH");
        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        }else if(request.getRequestURI().equals("/token")){
            filterChain.doFilter(request,response);
            return;
        }else {
            String jwt=request.getHeader(SecurityParams.JWT_HEADER_NAME);
            if (jwt==null || !jwt.startsWith(SecurityParams.HEADER_PREFIX)){
                filterChain.doFilter(request,response);
                return;
            }
            else {
                JWTVerifier verifier= JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
                DecodedJWT decodedJWT= verifier.verify(jwt.substring(SecurityParams.HEADER_PREFIX.length()));
                String username=decodedJWT.getSubject();

                UsernamePasswordAuthenticationToken user =new UsernamePasswordAuthenticationToken(username,null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(user);
                filterChain.doFilter(request,response);
            }
        }
    }
}

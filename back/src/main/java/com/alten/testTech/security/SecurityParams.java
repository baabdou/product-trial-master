package com.alten.testTech.security;

public interface SecurityParams {
    public static final String JWT_HEADER_NAME="Authorization";
    public static final String SECRET="baabdouaziz3@gmail.com";
    public static final long EXPIRATION=5*24*3600*1000;
    public static final String HEADER_PREFIX="Bearer ";
}

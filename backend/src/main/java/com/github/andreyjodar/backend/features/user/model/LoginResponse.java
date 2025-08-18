package com.github.andreyjodar.backend.features.user.model;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    @Value("${jwt.expiration}")
    private Long expiresIn;
    private UserResponse user;

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = new UserResponse(user);
    }
}

package com.github.andreyjodar.backend.features.auth.model;

import com.github.andreyjodar.backend.features.user.model.UserResponse;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private UserResponse user;

    public AuthResponse(String accessToken, UserResponse userResponse) {
        this.accessToken = accessToken;
        this.user = userResponse;
    }
}

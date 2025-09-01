package com.github.andreyjodar.backend.features.auth.model;

import com.github.andreyjodar.backend.features.user.model.UserResponse;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private UserResponse userResponse;

    public AuthResponse(String accessToken, UserResponse userResponse) {
        this.accessToken = accessToken;
        this.userResponse = userResponse;
    }
}

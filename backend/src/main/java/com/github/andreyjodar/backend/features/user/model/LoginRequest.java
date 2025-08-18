package com.github.andreyjodar.backend.features.user.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

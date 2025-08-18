package com.github.andreyjodar.backend.features.user.model;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String email;
    private String password;
}

package com.github.andreyjodar.backend.features.user.model;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
}

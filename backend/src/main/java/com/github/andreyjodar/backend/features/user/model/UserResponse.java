package com.github.andreyjodar.backend.features.user.model;

import java.util.Set;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
}

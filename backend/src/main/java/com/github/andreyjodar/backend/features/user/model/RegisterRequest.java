package com.github.andreyjodar.backend.features.user.model;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    @NotEmpty
    private Set<String> roles;
}

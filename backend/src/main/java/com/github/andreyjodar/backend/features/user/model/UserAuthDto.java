package com.github.andreyjodar.backend.features.user.model;

import lombok.Data;

@Data
public class UserAuthDto {
    private String name;
    private String email;
    private String token;
}

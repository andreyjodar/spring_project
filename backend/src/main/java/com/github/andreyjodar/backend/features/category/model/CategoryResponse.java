package com.github.andreyjodar.backend.features.category.model;

import com.github.andreyjodar.backend.features.user.model.UserResponse;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String note;
    private UserResponse userResponse;
}

package com.github.andreyjodar.backend.features.category.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank @Size(max = 50)
    private String name;
    @NotBlank @Size(max = 150)
    private String note;
}

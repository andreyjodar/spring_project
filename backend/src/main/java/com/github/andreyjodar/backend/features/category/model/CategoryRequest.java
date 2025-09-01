package com.github.andreyjodar.backend.features.category.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "{validation.categories.nameblank}") 
    @Size(max = 50, message = "{validation.categories.maxnamesize}")
    private String name;
    @NotBlank(message = "{validation.categories.notenull}") 
    @Size(max = 150, message = "{validation.categories.maxnotesize}")
    private String note;
}

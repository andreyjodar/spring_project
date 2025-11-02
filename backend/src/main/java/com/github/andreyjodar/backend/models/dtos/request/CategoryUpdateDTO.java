package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryUpdateDTO {
    @NotBlank(message = "{validation.categories.blankname}")
    private String name;
    private String note;
}

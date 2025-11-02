package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreationDTO {
    @NotBlank(message = "{validation.categories.blankname}")
    private String name;
    private String note;
}

package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryUpdateDTO {
    @NotBlank(message = "{validation.categories.blankname}")
    @Size(max = 50, message = "{validation.categories.outsizename}")
    private String name;
    @Size(max = 100, message = "{validation.categories.outsizenote}")
    private String note;
}

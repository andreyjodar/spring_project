package com.github.andreyjodar.backend.features.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditUserRequest {
    @NotBlank @Size(max = 100)
    private String name;
}

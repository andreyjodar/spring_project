package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileUpdateDTO {
    @NotBlank(message = "{validation.profiles.blankrole}")
    private String role;
}

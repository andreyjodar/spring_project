package com.github.andreyjodar.backend.features.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditUserRequest {
    @NotBlank(message = "{validation.users.nameblank}")
    @Size(max = 100, message = "{validation.users.namesize}")
    private String name;
}

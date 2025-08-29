package com.github.andreyjodar.backend.features.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank @Size(max = 100) @Email
    private String email;
    @NotBlank @Size(min = 6, max = 6)
    private String validityCode;
    @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")
    private String newPassword;
}

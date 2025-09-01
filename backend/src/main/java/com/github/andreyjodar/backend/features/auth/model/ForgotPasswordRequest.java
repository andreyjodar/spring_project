package com.github.andreyjodar.backend.features.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "{validation.users.emailblank}") 
    @Size(max = 100, message = "{validation.users.maxemailsize}") 
    @Email(message = "{validation.users.invalidemail}")
    private String email;
}

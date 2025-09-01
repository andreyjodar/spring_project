package com.github.andreyjodar.backend.features.auth.model;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "{validation.users.nameblank}") 
    @Size(max = 100, message = "{validation.users.maxnamesize}")
    private String name;
    @NotBlank(message = "{validation.users.emailblank}") 
    @Size(max = 100, message = "{validation.users.maxemailsize}") 
    @Email(message = "{validation.users.invalidemail}")
    private String email;
    @NotBlank(message = "{validation.users.passwblank}") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
    message = "{validation.users.invalidpassw}")
    private String password;
    @NotEmpty(message = "{validation.users.rolesempty}")
    private Set<String> roles;
}

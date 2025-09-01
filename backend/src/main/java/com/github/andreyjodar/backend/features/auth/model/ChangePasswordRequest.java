package com.github.andreyjodar.backend.features.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "{validation.users.emailblank}") 
    @Size(max = 100, message = "{validation.users.maxemailsize}") 
    @Email(message = "{validation.users.invalidemail}")
    private String email;
    @NotBlank(message = "{validation.users.validcodeblank}") 
    @Size(min = 6, max = 6, message = "{validation.users.validcodesize}")
    private String validityCode;
    @NotBlank(message = "{validation.users.passwblank}") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
    message = "{validation.users.invalidpassw}")
    private String newPassword;
}

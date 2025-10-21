package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "{validation.users.blankemail}") 
    @Size(max = 100, message = "{validation.users.outsizeemail}") 
    @Email(message = "{validation.users.invalidemail}")
    private String email;

    @NotBlank(message = "{validation.users.blankvaliditycode}") 
    @Size(min = 6, max = 6, message = "{validation.users.outsizevaliditycode}")
    private String validityCode;

    @NotBlank(message = "{validation.users.blankpassword}") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
    message = "{validation.users.invalidpassw}")
    private String newPassword;
}

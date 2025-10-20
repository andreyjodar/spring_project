package com.github.andreyjodar.backend.models.dtos.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationDTO {
    @NotBlank(message = "{validation.users.blankname}") 
    @Size(max = 100, message = "{validation.users.outsizename}")
    private String name;

    @NotBlank(message = "{validation.users.blankemail}") 
    @Email(message = "{validation.users.invalidemail}")
    @Size(max = 100, message = "{validation.users.outsizeemail}") 
    private String email;

    @NotBlank(message = "{validation.users.blankpassword}") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
    message = "{validation.users.invalidpassword}")
    private String password;

    @NotEmpty(message = "{validation.users.emptyroles}")
    private Set<String> profiles;
}

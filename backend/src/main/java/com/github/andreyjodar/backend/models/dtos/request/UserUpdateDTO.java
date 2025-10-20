package com.github.andreyjodar.backend.models.dtos.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @NotBlank(message = "{validation.users.blankname}") 
    @Size(max = 100, message = "{validation.users.outsizename}")
    private String name;

    @NotBlank(message = "{validation.users.blankemail}") 
    @Email(message = "{validation.users.invalidemail}")
    @Size(max = 100, message = "{validation.users.outsizeemail}") 
    private String email;

    @NotEmpty(message = "{validation.users.emptyroles}")
    private Set<String> profiles;
}

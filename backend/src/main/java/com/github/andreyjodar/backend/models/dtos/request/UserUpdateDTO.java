package com.github.andreyjodar.backend.models.dtos.request;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @Size(max = 100, message = "{validation.users.outsizename}")
    private String name;

    @NotEmpty(message = "{validation.users.emptyroles}")
    private Set<String> profiles;
}

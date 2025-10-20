package com.github.andreyjodar.backend.models.dtos.response;

import java.util.Set;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Set<String> profiles;
}

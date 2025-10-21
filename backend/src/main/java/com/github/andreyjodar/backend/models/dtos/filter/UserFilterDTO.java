package com.github.andreyjodar.backend.models.dtos.filter;

import java.util.Set;

import lombok.Data;

@Data
public class UserFilterDTO {
    private String name;
    private Set<String> profiles;
}

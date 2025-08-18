package com.github.andreyjodar.backend.features.user.model;

import java.util.Set;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Boolean active;
    private Set<String> roles;

    public UserResponse() {
        
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.active = user.getActive();
        this.roles = user.getRoles().stream()
            .map(Role::getName).collect(java.util.stream.Collectors.toSet());
    }
}

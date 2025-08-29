package com.github.andreyjodar.backend.features.user.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.features.auth.model.RegisterRequest;
import com.github.andreyjodar.backend.features.role.model.Role;
import com.github.andreyjodar.backend.features.role.model.RoleType;
import com.github.andreyjodar.backend.features.role.repository.RoleRepository;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.model.UserResponse;

@Component
public class UserMapper {
    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User fromDto(RegisterRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        if(userRequest.getRoles() != null) {
            Set<Role> roles = userRequest.getRoles().stream()
                .map(String::toUpperCase)
                .map(roleName -> RoleType.valueOf(roleName))
                .map(roleType -> roleRepository.findByType(roleType)
                    .orElseThrow(() -> new IllegalArgumentException("Role não encontrada: " + roleType))
                )
                .collect(Collectors.toSet());
            user.setRoles(roles);   
        }
        return user;
    }

    public UserResponse fromEntity(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());

        Set<String> roles = user.getRoles().stream()
            .map(role -> role.getType().name()) 
            .collect(Collectors.toSet());

        userResponse.setRoles(roles);

        return userResponse;
    }
}

package com.github.andreyjodar.backend.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Profile;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.services.interfaces.ProfileService;

@Mapper(componentModel = "spring") 
public abstract class UserMapper { 
    
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ProfileService profileService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "profiles", expression = "java(mapProfiles(dto.getProfiles()))")
    @Mapping(target = "validityCode", ignore = true)
    @Mapping(target = "expirationDate", ignore = true)
    @Mapping(target = "active", constant = "true") 
    @Mapping(target = "deleted", constant = "false")
    public abstract User toEntity(UserCreationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "name", expression = "java(dto.getName())")
    @Mapping(target = "profiles", expression = "java(mapProfiles(dto.getProfiles()))")
    @Mapping(target = "validityCode", ignore = true)
    @Mapping(target = "expirationDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    public abstract void updateEntityFromDto(UserUpdateDTO dto, @MappingTarget User entity);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getNewPassword()))")
    @Mapping(target = "validityCode", expression = "java(null)") 
    @Mapping(target = "expirationDate", expression = "java(null)")
    public abstract void updateEntityFromDto(ChangePasswordDTO dto, @MappingTarget User entity);

    @Mapping(target = "validityCode", expression = "java(validityCode)")
    @Mapping(target = "expirationDate", expression = "java(java.time.LocalDateTime.now().plusHours(1))")
    public abstract void updateEntityWithValidityCode(String validityCode, @MappingTarget User entity);

    protected Set<Profile> mapProfiles(Set<String> roles) {
        if (roles == null) {
            return new HashSet<>();
        }
        return roles.stream()
            .map(role -> profileService.findByRole(role)) 
            .collect(Collectors.toSet());
    }
}
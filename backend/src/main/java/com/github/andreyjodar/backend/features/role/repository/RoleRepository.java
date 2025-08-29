package com.github.andreyjodar.backend.features.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.role.model.Role;
import com.github.andreyjodar.backend.features.role.model.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByType(RoleType type);
}

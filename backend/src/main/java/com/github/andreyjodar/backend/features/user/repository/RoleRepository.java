package com.github.andreyjodar.backend.features.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}

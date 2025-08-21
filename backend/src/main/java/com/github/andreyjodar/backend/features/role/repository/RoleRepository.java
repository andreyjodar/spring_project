package com.github.andreyjodar.backend.features.role.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.andreyjodar.backend.features.role.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r WHERE r.name IN :names")
    Set<Role> findByNameIn(@Param("names") Set<String> names);
}

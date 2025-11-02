package com.github.andreyjodar.backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findById(Long id);
    Optional<Profile> findByRole(String role);
    Page<Profile> findAll(Pageable pageable);
}

package com.github.andreyjodar.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    boolean existsByAuctions_UserId(Long id);
    boolean existsByBids_UserId(Long id);
    boolean existsByPayments_UserId(Long id);
}

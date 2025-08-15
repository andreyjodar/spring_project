package com.github.andreyjodar.backend.features.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
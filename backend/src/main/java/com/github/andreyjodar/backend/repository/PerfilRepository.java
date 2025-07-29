package com.github.andreyjodar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    
}

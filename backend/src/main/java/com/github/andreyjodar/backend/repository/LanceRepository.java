package com.github.andreyjodar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.model.Lance;

public interface LanceRepository extends JpaRepository<Lance, Long>{
    
}

package com.github.andreyjodar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.model.Leilao;

public interface LeilaoRepository extends JpaRepository<Leilao, Long> {
    
}

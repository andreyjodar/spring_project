package com.github.andreyjodar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.model.Pessoa;

public interface PessoaRepositoy extends JpaRepository<Pessoa, Long> {
    
}

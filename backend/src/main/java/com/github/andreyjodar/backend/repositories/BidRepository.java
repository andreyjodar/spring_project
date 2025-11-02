package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.entities.Bid;

public interface BidRepository extends JpaRepository<Bid, Long>{
    boolean existsByBidderId(Long id);
}

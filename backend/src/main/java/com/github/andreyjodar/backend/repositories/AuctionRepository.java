package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.entities.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    boolean existsByAuctioneerId(Long id);
    boolean existsByCategoryId(Long id);
}

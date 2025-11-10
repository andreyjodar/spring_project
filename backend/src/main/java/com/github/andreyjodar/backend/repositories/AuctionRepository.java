package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.andreyjodar.backend.models.entities.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {
    boolean existsByAuctioneerId(Long id);
    boolean existsByCategoryId(Long id);
}

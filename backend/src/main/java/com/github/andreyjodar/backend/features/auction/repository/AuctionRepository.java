package com.github.andreyjodar.backend.features.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.auction.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    
}

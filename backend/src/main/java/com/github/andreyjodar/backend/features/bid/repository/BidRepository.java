package com.github.andreyjodar.backend.features.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.bid.model.Bid;

public interface BidRepository extends JpaRepository<Bid, Long>{
    
}

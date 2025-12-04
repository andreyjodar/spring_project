package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.andreyjodar.backend.models.entities.Bid;

public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid>{
    boolean existsByBidderId(Long id);
    boolean existsByAuctionId(Long id);
}

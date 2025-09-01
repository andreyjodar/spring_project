package com.github.andreyjodar.backend.features.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.user.model.User;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Page<Auction> findByAuctioneer(User user, Pageable pageable);
}

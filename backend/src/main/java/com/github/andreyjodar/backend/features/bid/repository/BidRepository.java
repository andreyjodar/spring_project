package com.github.andreyjodar.backend.features.bid.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.user.model.User;

public interface BidRepository extends JpaRepository<Bid, Long>{
    Page<Bid> findByBidder(User bidder, Pageable pageable);
}

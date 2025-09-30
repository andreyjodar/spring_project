package com.github.andreyjodar.backend.features.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByAuction(Auction auction);

    @Query("FROM Payment WHERE auction.id=:auctionId")
    Optional<Payment> findByAuctionId(@Param("auctionId") Long auctionId);
}

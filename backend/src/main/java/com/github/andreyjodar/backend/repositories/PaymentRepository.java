package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByBuyerId(Long id);
}

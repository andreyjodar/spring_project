package com.github.andreyjodar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
}
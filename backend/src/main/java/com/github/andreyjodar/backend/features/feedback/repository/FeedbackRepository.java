package com.github.andreyjodar.backend.features.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.feedback.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
}
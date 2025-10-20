package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
}

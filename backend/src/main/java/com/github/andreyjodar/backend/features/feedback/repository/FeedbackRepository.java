package com.github.andreyjodar.backend.features.feedback.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.feedback.model.Feedback;
import com.github.andreyjodar.backend.features.user.model.User;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Page<Feedback> findByAuthor(User author, Pageable pageable);
    Page<Feedback> findByRecipient(User recipient, Pageable pageable);
}
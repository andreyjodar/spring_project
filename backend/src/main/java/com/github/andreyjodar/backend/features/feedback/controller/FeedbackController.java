package com.github.andreyjodar.backend.features.feedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.features.feedback.model.Feedback;
import com.github.andreyjodar.backend.features.feedback.model.FeedbackRequest;
import com.github.andreyjodar.backend.features.feedback.service.FeedbackService;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired 
    private AuthUserProvider authUserProvider;

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(feedbackService.createFeedback(authUser, feedbackRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable("id") Long id, @Valid @RequestBody FeedbackRequest feedbackRequest) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(feedbackService.updateFeedback(id, authUser, feedbackRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable("id") Long id) {
        User authUser = authUserProvider.getAutheticatedUser();
        feedbackService.deleteFeedback(id, authUser);
        return ResponseEntity.ok("Feedback was Deleted Successfully!");
    }

    @GetMapping
    public ResponseEntity<Page<Feedback>> findAll(Pageable pageable) {
        return ResponseEntity.ok(feedbackService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @GetMapping("/received")
    public ResponseEntity<Page<Feedback>> getMyRecipientFeedback(Pageable pageable) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(feedbackService.findByRecipient(authUser, pageable));
    }

    @GetMapping("/given")
    public ResponseEntity<Page<Feedback>> getMyAuthorFeedback(Pageable pageable) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(feedbackService.findByAuthor(authUser, pageable));
    }
}

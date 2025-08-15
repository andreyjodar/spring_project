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

import com.github.andreyjodar.backend.features.feedback.model.Feedback;
import com.github.andreyjodar.backend.features.feedback.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<Page<Feedback>> findAll(Pageable pageable) {
        return ResponseEntity.ok(feedbackService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Feedback> create(@Valid @RequestBody Feedback feedback) {
        return ResponseEntity.ok(feedbackService.create(feedback));
    }

    @PutMapping
    public ResponseEntity<Feedback> update(@Valid @RequestBody Feedback feedback) {
        return ResponseEntity.ok(feedbackService.update(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        feedbackService.delete(id);
        return ResponseEntity.ok("Excluído");
    }
}

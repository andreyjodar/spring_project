package com.github.andreyjodar.backend.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.models.dtos.filter.FeedbackFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.models.entities.Feedback;
import com.github.andreyjodar.backend.services.interfaces.FeedbackService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/feedbacks")
@AllArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final MessageSource messageSource;

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Feedback>> getFiltered(@Valid FeedbackFilterDTO feedbackFilterDTO, Pageable pageable) {
        return ResponseEntity.ok(feedbackService.findFiltered(feedbackFilterDTO, pageable));
    }
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Feedback> create(@Valid @RequestBody FeedbackCreationDTO feedbackCreationDTO) {
        return ResponseEntity.ok(feedbackService.create(feedbackCreationDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Feedback> update(@PathVariable("id") Long id, @Valid @RequestBody FeedbackUpdateDTO feedbackUpdateDTO) {
        return ResponseEntity.ok(feedbackService.update(id, feedbackUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SimpleResponseDTO> delete(@PathVariable("id") Long id) {
        feedbackService.delete(id);
        return ResponseEntity.ok(new SimpleResponseDTO(messageSource.getMessage("success.feedbacks.deleted",
            new Object[] { id }, LocaleContextHolder.getLocale())));
    }
}

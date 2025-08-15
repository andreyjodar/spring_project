package com.github.andreyjodar.backend.features.feedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.feedback.model.Feedback;
import com.github.andreyjodar.backend.features.feedback.repository.FeedbackRepository;
import com.github.andreyjodar.backend.shared.services.EmailService;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    public Feedback create(Feedback feedback) {
        Feedback feedbackAtual = feedbackRepository.save(feedback);
        sendFeedbackEmail(feedbackAtual);
        return feedbackAtual;
    }

    public Feedback update(Feedback feedback) {
        Feedback feedbackDb = findById(feedback.getId());
        feedbackDb.setComment(feedback.getComment());
        feedbackDb.setGrade(feedback.getGrade());
        feedbackDb.setUpdatedAt(feedback.getUpdatedAt());
        return feedbackRepository.save(feedbackDb);
    }

    public void sendFeedbackEmail(Feedback feedback) {
        Context context = new Context();
        context.setVariable("nome", feedback.getAuthor());
        emailService.emailTemplate(feedback.getRecipient().getName(), "Recebeu um Feedback", context, feedback.getComment());
    }

    public void delete(Long id) {
        Feedback feedbackDb = findById(id);
        feedbackRepository.delete(feedbackDb);
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Feedback> findAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }
}
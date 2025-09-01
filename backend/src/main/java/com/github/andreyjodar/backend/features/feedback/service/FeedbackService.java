package com.github.andreyjodar.backend.features.feedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.feedback.mapper.FeedbackMapper;
import com.github.andreyjodar.backend.features.feedback.model.Feedback;
import com.github.andreyjodar.backend.features.feedback.model.FeedbackRequest;
import com.github.andreyjodar.backend.features.feedback.repository.FeedbackRepository;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.service.UserService;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FeedbackMapper feedbackMapper;

    public Feedback createFeedback(User authUser, FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackMapper.fromDto(feedbackRequest);
        feedback.setAuthor(authUser);

        if(authUser.getId().equals(feedbackRequest.getRecipientId())) {
            throw new BusinessException(messageSource.getMessage("exception.feedbacks.notvalid",
                new Object[] { authUser.getId() }, LocaleContextHolder.getLocale()));
        }

        User recipient = userService.findById(feedbackRequest.getRecipientId());
        feedback.setRecipient(recipient);
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Long id, User authUser, FeedbackRequest feedbackRequest) {
        Feedback feedback = findById(id);

        if(!authUser.isAdmin() && !feedback.getAuthor().equals(authUser)) {
            throw new BusinessException(messageSource.getMessage("exception.feedbacks.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        feedback.setComment(feedbackRequest.getComment());
        feedback.setGrade(feedbackRequest.getGrade());
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id, User authUser) {
        Feedback feedback = findById(id);
        if(!authUser.isAdmin() && !feedback.getAuthor().equals(authUser)) {
            throw new BusinessException(messageSource.getMessage("exception.feedbacks.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        feedbackRepository.delete(feedback);
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Feedback> findByAuthor(User author, Pageable pageable) {
        return feedbackRepository.findByAuthor(author, pageable);
    }

    public Page<Feedback> findByRecipient(User recipient, Pageable pageable) {
        return feedbackRepository.findByRecipient(recipient, pageable);
    }

    public Page<Feedback> findAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }
}
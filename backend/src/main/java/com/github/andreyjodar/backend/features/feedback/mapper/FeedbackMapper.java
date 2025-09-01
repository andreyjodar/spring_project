package com.github.andreyjodar.backend.features.feedback.mapper;

import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.features.feedback.model.Feedback;
import com.github.andreyjodar.backend.features.feedback.model.FeedbackRequest;

@Component
public class FeedbackMapper {
    
    public Feedback fromDto(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();
        feedback.setGrade(feedbackRequest.getGrade());
        feedback.setComment(feedbackRequest.getComment());
        return feedback;
    }
}

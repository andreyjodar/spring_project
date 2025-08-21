package com.github.andreyjodar.backend.features.feedback.model;

import com.github.andreyjodar.backend.features.user.model.UserResponse;

import lombok.Data;

@Data
public class FeedbackResponse {
    private Long id;
    private Integer grade;
    private String comment;
    private UserResponse authResponse;
    private UserResponse recipientResponse;
}

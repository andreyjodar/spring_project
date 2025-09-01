package com.github.andreyjodar.backend.features.feedback.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackRequest {
    @NotNull(message = "{validation.feedbacks.gradenull}") 
    @Min(value = 1, message = "{validation.feedbacks.mingrade}") 
    @Max(value = 5, message = "{validation.feedbacks.maxgrade}")
    private Integer grade;
    @NotBlank(message = "{validation.feedbacks.commentnull}") 
    @Size(max = 255, message = "{validation.feedbacks.commentsize}")
    private String comment;
    @NotNull(message = "{validation.feedbacks.recipientidnull}")
    private Long recipientId;
}

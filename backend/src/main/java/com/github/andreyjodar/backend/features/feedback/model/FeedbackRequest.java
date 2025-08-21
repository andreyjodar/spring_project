package com.github.andreyjodar.backend.features.feedback.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackRequest {
    @NotNull @Min(1) @Max(5)
    private Integer grade;
    @NotBlank @Size(max = 255)
    private String comment;
    @NotNull
    private Long recipientId;
}

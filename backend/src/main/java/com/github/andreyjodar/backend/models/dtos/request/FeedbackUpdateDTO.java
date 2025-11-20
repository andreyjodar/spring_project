package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackUpdateDTO {
    @Min(value = 1, message = "{validation.feedbacks.mingrade}")
    @Max(value = 5, message = "{validation.feedbacks.maxgrade}")
    private Integer grade;

    @Size(message = "{validation.feedbacks.outsizecomment}")
    private String comment;
}

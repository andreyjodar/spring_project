package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackCreationDTO {
    @NotNull(message = "{validation.feedbacks.gradenull}")
    @Min(value = 1, message = "{validation.feedbacks.mingrade}")
    @Max(value = 5, message = "{validation.feedbacks.maxgrade}")
    private Integer grade;

    @NotBlank(message = "{validation.feedbacks.blankcomment}")
    @Size(message = "{validation.feedbacks.outsizecomment}")
    private String comment;

    @NotNull(message = "{validation.feedbacks.nullauctionid}")
    private Long auctionId;
}

package com.github.andreyjodar.backend.models.dtos.filter;

import lombok.Data;

@Data
public class FeedbackFilterDTO {
    private Long auctionId;
    private Long authorId;
    private Integer grade;
}

package com.github.andreyjodar.backend.models.dtos.filter;

import java.time.LocalDateTime;

import com.github.andreyjodar.backend.models.enums.AuctionStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AuctionFilterDTO {
    private String title; 
    private Long categoryId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @Enumerated(EnumType.STRING)
    private AuctionStatus status;
}

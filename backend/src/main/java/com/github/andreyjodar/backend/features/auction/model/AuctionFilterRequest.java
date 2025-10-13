package com.github.andreyjodar.backend.features.auction.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuctionFilterRequest {
    private String title; 
    private Long categoryId; 
    private AuctionStatus status; 
    private LocalDateTime startDateMin; 
    private LocalDateTime startDateMax; 
}

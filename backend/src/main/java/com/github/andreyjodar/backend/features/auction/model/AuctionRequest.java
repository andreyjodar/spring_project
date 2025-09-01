package com.github.andreyjodar.backend.features.auction.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuctionRequest {
    @NotBlank @Size(max = 100)
    private String title;
    @NotBlank @Size(max = 200)
    private String description;
    @Size(max = 255)
    private String expandedDescription;
    @NotNull
    private Long categoryId;
    @NotNull @FutureOrPresent
    private LocalDateTime startDateTime;
    @NotNull @Future
    private LocalDateTime endDateTime;
    @NotNull @Positive
    private Float minBid;
}

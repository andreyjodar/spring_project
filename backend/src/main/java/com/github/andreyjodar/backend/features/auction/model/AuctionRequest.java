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
    @NotBlank(message = "{validation.auctions.titlenull}") 
    @Size(max = 100, message = "{validation.auctions.maxtitlesize}")
    private String title;
    @NotBlank(message = "{validation.auctions.descnull}") 
    @Size(max = 200, message = "{validation.auctions.maxdescsize}")
    private String description;
    @Size(max = 255, message = "{validation.auctions.maxexpdescsize}")
    private String expandedDescription;
    @NotNull(message = "{validation.auctions.categoryidnull}")
    private Long categoryId;
    @NotNull(message = "{validation.auctions.startdatenull}") 
    @FutureOrPresent(message = "{validation.auctions.startpresorfuture}")
    private LocalDateTime startDateTime;
    @NotNull(message = "{validation.auctions.enddatenull}") 
    @Future(message = "{validation.auctions.enddatefuture}")
    private LocalDateTime endDateTime;
    @NotNull(message = "{validation.auctions.minbidnull}") 
    @Positive(message = "{validation.auctions.minbidpositive}")
    private Float minBid;
}

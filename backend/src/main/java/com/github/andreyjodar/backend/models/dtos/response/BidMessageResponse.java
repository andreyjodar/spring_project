package com.github.andreyjodar.backend.models.dtos.response;

import lombok.Data;

@Data
public class BidMessageResponse {
    private Long auctionId;
    private Double currentPrice;
    private String currentBidder;
    private String message;
}

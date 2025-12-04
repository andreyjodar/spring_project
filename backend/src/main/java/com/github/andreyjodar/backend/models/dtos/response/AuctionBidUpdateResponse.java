package com.github.andreyjodar.backend.models.dtos.response;

import lombok.Data;

@Data
public class AuctionBidUpdateResponse {
    private Long id;
    private Double currentPrice;
    private String currentBidder;

    public AuctionBidUpdateResponse(Long id, Double currentPrice, String currentBidder) {
        this.id = id;
        this.currentPrice = currentPrice;
        this.currentBidder = currentBidder;
    }
}

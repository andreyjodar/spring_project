package com.github.andreyjodar.backend.features.bid.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BidRequest {
    @NotNull(message = "{validation.bids.valuenull}")
    @Positive(message = "{validation.bids.valuepositive}")
    private Float bidValue;
    @NotNull(message = "{validation.bids.auctionidnull}")
    private Long auctionid;
}

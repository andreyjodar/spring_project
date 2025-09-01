package com.github.andreyjodar.backend.features.bid.model;

import lombok.Data;

@Data
public class BidRequest {
    private Float bidValue;
    private Long auctionid;
}

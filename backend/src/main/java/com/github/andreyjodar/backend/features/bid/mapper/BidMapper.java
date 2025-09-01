package com.github.andreyjodar.backend.features.bid.mapper;

import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.bid.model.BidRequest;

@Component
public class BidMapper {
    
    public Bid fromDto(BidRequest bidRequest) {
        Bid bid = new Bid();
        bid.setBidValue(bidRequest.getBidValue());
        return bid;
    }
}

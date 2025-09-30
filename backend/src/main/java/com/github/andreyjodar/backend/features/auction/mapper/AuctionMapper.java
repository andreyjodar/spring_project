package com.github.andreyjodar.backend.features.auction.mapper;

import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.model.AuctionRequest;
import com.github.andreyjodar.backend.features.auction.model.AuctionStatus;

@Component
public class AuctionMapper {

    public AuctionMapper() {};
    
    public Auction fromDto(AuctionRequest auctionRequest) {
        Auction auction = new Auction();
        auction.setTitle(auctionRequest.getTitle());
        auction.setDescription(auctionRequest.getDescription());
        auction.setExpandedDescription(auctionRequest.getExpandedDescription());
        auction.setStartDateTime(auctionRequest.getStartDateTime());
        auction.setStatus(AuctionStatus.valueOf(auctionRequest.getStatus()));
        auction.setEndDateTime(auction.getEndDateTime());
        auction.setMinBid(auctionRequest.getMinBid());
        return auction;
    }
}

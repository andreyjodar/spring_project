// package com.github.andreyjodar.backend.features.auction.mapper;

// import org.springframework.stereotype.Component;

// import com.github.andreyjodar.backend.features.auction.model.Auction;
// import com.github.andreyjodar.backend.features.auction.model.AuctionCreateRequest;
// import com.github.andreyjodar.backend.features.auction.model.AuctionEditRequest;

// @Component
// public class AuctionMapper {

//     public AuctionMapper() {};
    
//     public Auction fromDto(AuctionCreateRequest auctionRequest) {
//         Auction auction = new Auction();
//         auction.setTitle(auctionRequest.getTitle());
//         auction.setDescription(auctionRequest.getDescription());
//         auction.setExpandedDescription(auctionRequest.getExpandedDescription());
//         auction.setStartDateTime(auctionRequest.getStartDateTime());
//         auction.setEndDateTime(auctionRequest.getEndDateTime());
//         return auction;
//     }

//     public Auction updateAuction(Auction auction, AuctionEditRequest auctionRequest) {
//         auction.setTitle(auctionRequest.getTitle());
//         auction.setDescription(auctionRequest.getDescription());
//         auction.setExpandedDescription(auctionRequest.getExpandedDescription());
//         auction.setStartDateTime(auctionRequest.getStartDateTime());
//         auction.setEndDateTime(auction.getEndDateTime());
//         return auction;
//     }
// }

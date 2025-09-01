package com.github.andreyjodar.backend.features.auction.mapper;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.model.AuctionRequest;
import com.github.andreyjodar.backend.features.auction.model.AuctionStatus;

@Component
public class AuctionMapper {
    private final MessageSource messageSource;

    public AuctionMapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public Auction fromDto(AuctionRequest auctionRequest) {
        Auction auction = new Auction();
        auction.setTitle(auctionRequest.getTitle());
        auction.setDescription(auctionRequest.getDescription());
        auction.setExpandedDescription(auctionRequest.getExpandedDescription());
        auction.setStartDateTime(auctionRequest.getStartDateTime());
        setAuctionStatus(auction);
        auction.setEndDateTime(auction.getEndDateTime());
        auction.setMinBid(auctionRequest.getMinBid());
        return auction;
    }

    private void setAuctionStatus(Auction auction) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if(auction.getStartDateTime().isAfter(auction.getEndDateTime())) {
            throw new IllegalArgumentException(messageSource.getMessage("exception.auctions.ivalidperiod",
                new Object[] { auction.getTitle() }, LocaleContextHolder.getLocale()));
        }

        if(auction.getStartDateTime().isAfter(currentDateTime)) {
            auction.setStatus(AuctionStatus.OPEN);
        }

        if(auction.getStartDateTime().isBefore(currentDateTime) && auction.getEndDateTime().isAfter(currentDateTime)) {
            auction.setStatus(AuctionStatus.ANALYSING);
        }

        if(auction.getEndDateTime().isBefore(currentDateTime)) {
            auction.setStatus(AuctionStatus.CLOSED);
        }
    }
}

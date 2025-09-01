package com.github.andreyjodar.backend.features.bid.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.ForbiddenException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.service.AuctionService;
import com.github.andreyjodar.backend.features.bid.mapper.BidMapper;
import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.bid.model.BidRequest;
import com.github.andreyjodar.backend.features.bid.repository.BidRepository;
import com.github.andreyjodar.backend.features.user.model.User;

@Service
public class BidService {
    
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidMapper bidMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AuctionService auctionService;

    public Bid createBid(User authUser, BidRequest bidRequest) {
        Bid bid = bidMapper.fromDto(bidRequest);
        Auction auction = auctionService.findById(bidRequest.getAuctionid());
        if(!authUser.isBuyer()) {
            throw new ForbiddenException(messageSource.getMessage("exception.bids.forbidden",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        if(auction.getAuctioneer().equals(authUser)) {
            throw new BusinessException(messageSource.getMessage("exception.bids.notvalid",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        bid.setBidder(authUser);
        if(!isValidTerm(auction)) {
            throw new BusinessException(messageSource.getMessage("exception.bids.invalidterm",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        if(!isValidPrice(auction, bid)) {
            throw new BusinessException(messageSource.getMessage("exception.bids.invalidprice",
                new Object[] { auction.getMinBid() + auction.getIncrementValue() }, LocaleContextHolder.getLocale()));
        }

        auctionService.updatePrice(auction, bid.getBidValue());
        auctionService.updateStatus(auction);
        bid.setAuction(auction);
        return bidRepository.save(bid);
    }

    private Boolean isValidTerm(Auction auction) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.isAfter(auction.getStartDateTime()) && currentDateTime.isBefore(auction.getEndDateTime());
    }

    private Boolean isValidPrice(Auction auction, Bid bid) {
        return auction.getMinBid() + auction.getIncrementValue() < bid.getBidValue();
    }

    public Bid findById(Long id) {
        return bidRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.bids.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Bid> findByBidder(User bidder, Pageable pageable) {
        return bidRepository.findByBidder(bidder, pageable);
    }

    public Page<Bid> findAll(Pageable pageable) {
        return bidRepository.findAll(pageable);
    }
}

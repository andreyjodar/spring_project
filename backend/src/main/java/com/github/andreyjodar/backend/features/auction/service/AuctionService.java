package com.github.andreyjodar.backend.features.auction.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.auction.mapper.AuctionMapper;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.model.AuctionRequest;
import com.github.andreyjodar.backend.features.auction.model.AuctionStatus;
import com.github.andreyjodar.backend.features.auction.repository.AuctionRepository;
import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.category.service.CategoryService;
import com.github.andreyjodar.backend.features.user.model.User;

@Service
public class AuctionService {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired 
    private CategoryService categoryService;

    @Autowired
    private AuctionMapper auctionMapper;

    @Autowired
    private MessageSource messageSource;

    public Auction createAuction(User authUser, AuctionRequest auctionRequest) {
        if(!authUser.isSeller()) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.notseller",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        if(!isValidPeriod(auctionRequest.getStartDateTime(), auctionRequest.getEndDateTime())) {
            throw new IllegalArgumentException(messageSource.getMessage("exception.auctions.invalidperiod",
                new Object[] {auctionRequest.getStartDateTime(), auctionRequest.getEndDateTime()}, LocaleContextHolder.getLocale()));
        }

        Auction auction = auctionMapper.fromDto(auctionRequest);
        auction.setCategory(categoryService.findById(auctionRequest.getCategoryId()));
        if(!authUser.isAdmin() && !auction.getCategory().getAuthor().equals(authUser)) {
            throw new BusinessException(messageSource.getMessage("exception.categories.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        auction.setAuctioneer(authUser);
        auction.setStatus(AuctionStatus.ACTIVE);
        return auctionRepository.save(auction);
    }

    public Auction updateAuction(Long id, User authUser, AuctionRequest auctionRequest) {
        if(!authUser.isSeller()) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.notseller",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        if(!isValidPeriod(auctionRequest.getStartDateTime(), auctionRequest.getEndDateTime())) {
            throw new IllegalArgumentException(messageSource.getMessage("exception.auctions.invalidperiod",
                new Object[] {auctionRequest.getStartDateTime(), auctionRequest.getEndDateTime()}, LocaleContextHolder.getLocale()));
        }

        Auction auction = findById(id);
        if(!authUser.isAdmin() && !auction.getAuctioneer().equals(authUser)) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        Category category = categoryService.findById(auctionRequest.getCategoryId());
        
        auction.setTitle(auctionRequest.getTitle());
        auction.setDescription(auctionRequest.getDescription());
        auction.setExpandedDescription(auctionRequest.getExpandedDescription());
        auction.setCategory(category);
        auction.setStatus(AuctionStatus.valueOf(auctionRequest.getStatus()));
        auction.setStartDateTime(auctionRequest.getStartDateTime());
        auction.setEndDateTime(auctionRequest.getEndDateTime());
        return auctionRepository.save(auction);
    }

    public void deleteAuction(Long id, User authUser) {
        if(!authUser.isSeller()) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.notseller",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        Auction auction = findById(id);
        if(!authUser.isAdmin() && !auction.getAuctioneer().equals(authUser)) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        auctionRepository.delete(auction); 
    }

    public Auction updatePrice(Auction auction, Float newPrice) {
        Float currentPrice = auction.getMinBid() + auction.getIncrementValue();
        if(newPrice <= currentPrice) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.invalidprice",
                new Object[] { currentPrice }, LocaleContextHolder.getLocale()));
        }

        Float offsetValue = newPrice - auction.getMinBid();
        auction.setIncrementValue(offsetValue);
        return auctionRepository.save(auction);
    }

    private Boolean isValidPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.isBefore(endDateTime);
    }

    public Auction findById(Long id) {
        return auctionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.auctions.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Auction> findByAuctioneer(User user, Pageable pageable) {
        return auctionRepository.findByAuctioneer(user, pageable);
    }

    public Page<Auction> findAll(Pageable pageable) {
        return auctionRepository.findAll(pageable);
    }
}

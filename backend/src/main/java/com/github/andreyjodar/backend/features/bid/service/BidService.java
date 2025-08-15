package com.github.andreyjodar.backend.features.bid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.service.AuctionService;
import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.bid.repository.BidRepository;
import com.github.andreyjodar.backend.shared.services.EmailService;

@Service
public class BidService {
    
    @Autowired
    BidRepository bidRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    EmailService emailService;

    @Autowired
    AuctionService auctionService;

    public Bid create(Bid bid) {
        Auction auction = auctionService.findById(bid.getAuction().getId());
        Float currentValue = auction.getMinBid() + auction.getIncrementValue();
        if(bid.getBidValue() > currentValue) throw new BusinessException("Lance deve ser superior a R$" + currentValue);
        Float novoIncremento = bid.getBidValue() - auction.getMinBid();
        auction.setIncrementValue(novoIncremento);
        auctionService.update(auction); 
        return bidRepository.save(bid);
    }

    public void delete(Long id) {

    }

    // public Bid update(Bid bid) {

    // }

    public Bid findById(Long id) {
        return bidRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Bid> findAll(Pageable pageable) {
        return bidRepository.findAll(pageable);
    }
}

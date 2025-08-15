package com.github.andreyjodar.backend.features.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.repository.AuctionRepository;

@Service
public class AuctionService {
    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    MessageSource messageSource;

    public Auction create(Auction auction) {
        return auctionRepository.save(auction);
    }

    public Auction update(Auction auction) {
        Auction leilaoBanco = findById(auction.getId());
        leilaoBanco.setCategory(auction.getCategory());
        leilaoBanco.setTitle(auction.getTitle());
        leilaoBanco.setDescription(auction.getDescription());
        leilaoBanco.setExpandedDescription(auction.getExpandedDescription());
        leilaoBanco.setStartDateTime(auction.getStartDateTime());
        leilaoBanco.setEndDateTime(auction.getEndDateTime());
        leilaoBanco.setStatus(auction.getStatus());
        leilaoBanco.setIncrementValue(auction.getIncrementValue());
        leilaoBanco.setMinBid(auction.getMinBid());
        return auctionRepository.save(leilaoBanco);
    }

    public void delete(Long id) {
        Auction leilaoBanco = findById(id);
        auctionRepository.delete(leilaoBanco); 
    }

    public Auction findById(Long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Auction> findAll(Pageable pageable) {
        return auctionRepository.findAll(pageable);
    }

}

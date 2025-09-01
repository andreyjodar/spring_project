package com.github.andreyjodar.backend.features.bid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.bid.model.BidRequest;
import com.github.andreyjodar.backend.features.bid.service.BidService;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bids")
public class BidController {
    
    @Autowired
    private BidService bidService;

    @Autowired
    private AuthUserProvider authUserProvider;

    @PostMapping
    public ResponseEntity<Bid> createBid(@Valid @RequestBody BidRequest bidRequest) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(bidService.createBid(authUser, bidRequest));
    }

    @GetMapping
    public ResponseEntity<Page<Bid>> getAllBids(Pageable pageable) {
        return ResponseEntity.ok(bidService.findAll(pageable));
    } 

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bidService.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<Bid>> getMyBids(Pageable pageable) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(bidService.findByBidder(authUser, pageable));
    }
}

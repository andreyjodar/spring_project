package com.github.andreyjodar.backend.features.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.model.AuctionFilterRequest;
import com.github.andreyjodar.backend.features.auction.model.AuctionCreateRequest;
import com.github.andreyjodar.backend.features.auction.model.AuctionEditRequest;
import com.github.andreyjodar.backend.features.auction.service.AuctionService;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuthUserProvider authUserProvider;

    @PostMapping
    public ResponseEntity<Auction> createAuction(@Valid @RequestBody AuctionCreateRequest auctionRequest) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(auctionService.createAuction(authUser, auctionRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable("id") Long id, @Valid @RequestBody AuctionEditRequest auctionRequest) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(auctionService.updateAuction(id, authUser, auctionRequest));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAuction(@PathVariable("id") Long id) {
        User authUser = authUserProvider.getAutheticatedUser();
        auctionService.cancelAuction(id, authUser);
        return ResponseEntity.ok("Auction canceled successfully!");
    }

    @PutMapping("/{id}/active") 
    public ResponseEntity<String> activeAuction(@PathVariable("id") Long id) {
        User authUser = authUserProvider.getAutheticatedUser();
        auctionService.activeAuction(id, authUser);
        return ResponseEntity.ok("Auction canceled successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuction(@PathVariable("id") Long id) {
        User authUser = authUserProvider.getAutheticatedUser();
        auctionService.deleteAuction(id, authUser);
        return ResponseEntity.ok("Auction was deleted successfully!");
    }

    @GetMapping
    public ResponseEntity<Page<Auction>> getFilteredAuctions(AuctionFilterRequest filterRequest, Pageable pageable) { 
        Page<Auction> auctions = auctionService.findFiltered(filterRequest, pageable);
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(auctionService.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<Auction>> getMyAuctions(Pageable pageable) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(auctionService.findByAuctioneer(authUser, pageable));
    }
}

package com.github.andreyjodar.backend.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.models.dtos.filter.BidFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.BidCreationDTO;
import com.github.andreyjodar.backend.models.entities.Bid;
import com.github.andreyjodar.backend.services.interfaces.BidService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/bids")
@AllArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Bid> create(@RequestBody @Valid BidCreationDTO bidCreationDTO) {
        return ResponseEntity.ok(bidService.create(bidCreationDTO));
    }

    @GetMapping
    public ResponseEntity<Page<Bid>> getFiltered(BidFilterDTO bidFilterDTO, Pageable pageable) {
        return ResponseEntity.ok(bidService.findFiltered(bidFilterDTO, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bidService.findById(id));
    }
}

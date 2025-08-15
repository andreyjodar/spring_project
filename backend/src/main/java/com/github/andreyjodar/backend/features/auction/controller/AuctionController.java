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

import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.auction.service.AuctionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @GetMapping
    public ResponseEntity<Page<Auction>> buscarTodos(Pageable pageable) {
        return ResponseEntity.ok(auctionService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Auction> inserir(@Valid @RequestBody Auction feedback) {
        return ResponseEntity.ok(auctionService.create(feedback));
    }

    @PutMapping
    public ResponseEntity<Auction> alterar(@Valid @RequestBody Auction feedback) {
        return ResponseEntity.ok(auctionService.update(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        auctionService.delete(id);
        return ResponseEntity.ok("Excluído");
    }
}

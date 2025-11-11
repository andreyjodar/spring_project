package com.github.andreyjodar.backend.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.models.dtos.filter.AuctionFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.services.interfaces.AuctionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auctions")
@AllArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;
    private final MessageSource messageSource;

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(auctionService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Auction>> getFiltered(@Valid AuctionFilterDTO auctionFilterDTO, Pageable pageable) {
        return ResponseEntity.ok(auctionService.findFiltered(auctionFilterDTO, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SELLER') || hasAuthority('ADMIN')")
    public ResponseEntity<Auction> create(@RequestBody @Valid AuctionCreationDTO auctionCreationDTO) {
        return ResponseEntity.ok(auctionService.create(auctionCreationDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SELLER') || hasAuthority('ADMIN')")
    public ResponseEntity<Auction> update(@PathVariable("id") Long id, @RequestBody @Valid AuctionUpdateDTO auctionUpdateDTO) {
        return ResponseEntity.ok(auctionService.update(id, auctionUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SELLER') || hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponseDTO> delete(@PathVariable("id") Long id) {
        auctionService.delete(id);
        return ResponseEntity.ok(new SimpleResponseDTO(messageSource.getMessage("success.auctions.deleted",
            new Object[] { id }, LocaleContextHolder.getLocale())));
    }
}

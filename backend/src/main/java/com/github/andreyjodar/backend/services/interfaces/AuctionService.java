package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.andreyjodar.backend.models.dtos.filter.AuctionFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Auction;

public interface AuctionService {
    Auction findById(Long id);
    Page<Auction> findFiltered(AuctionFilterDTO auctionFilterDTO, Pageable pageable);
    Auction create(AuctionCreationDTO auctionCreationDTO);
    Auction update(Long id, AuctionUpdateDTO auctionUpdateDTO);
    void delete(Long id);
}

package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.andreyjodar.backend.models.dtos.filter.AuctionFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.User;

public interface AuctionService {
    Auction findById(Long id);
    Page<Auction> findFiltered(AuctionFilterDTO auctionFilterDTO, Pageable pageable);
    Auction create(AuctionCreationDTO auctionCreationDTO);
    Auction update(Long id, AuctionUpdateDTO auctionUpdateDTO);
    Auction updatePrice(Auction auction, String newBidder, Double newPrice);
    void validateUpdate(User authUser, Auction auction);
    public void validateActive(Auction auction);
    void delete(Long id);
}

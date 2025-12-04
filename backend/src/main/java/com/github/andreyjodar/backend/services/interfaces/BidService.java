package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.andreyjodar.backend.models.dtos.filter.BidFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.BidCreationDTO;
import com.github.andreyjodar.backend.models.entities.Bid;

public interface BidService {
    public Bid findById(Long id);
    public Page<Bid> findFiltered(BidFilterDTO bidFilterDTO, Pageable pageable);
    public Bid create(BidCreationDTO bidCreationDTO);
}

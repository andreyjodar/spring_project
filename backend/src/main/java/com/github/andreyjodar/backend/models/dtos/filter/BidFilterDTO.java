package com.github.andreyjodar.backend.models.dtos.filter;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BidFilterDTO {
    private Long auctionId;
    private Long bidderId;
    private Double minPrice;
    private Double maxPrice;
    private LocalDateTime minDateTime;
    private LocalDateTime maxDateTime;
}

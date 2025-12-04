package com.github.andreyjodar.backend.models.dtos.filter;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentFilterDTO {
    private Long buyerId;
    private Long auctionId;
    private LocalDateTime minDateTime;
    private LocalDateTime maxDateTime;
    private Double minPrice;
    private Double maxPrice;
}

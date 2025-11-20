package com.github.andreyjodar.backend.models.dtos.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuctionUpdateDTO {
    @Size(max = 100, message = "{validation.auctions.outsizetitle}")
    private String title;

    @Size(max = 200, message = "{validation.auctions.outsizedesc}")
    private String description;

    private String expandedDescription;

    private Long categoryId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Positive(message = "{validation.auctions.positiveprice}")
    private Double currentPrice;
}

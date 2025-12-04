package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BidCreationDTO {
    @NotNull(message = "{validation.bids.nullauctionid}")
    private Long auctionId;

    @NotNull(message = "{validation.bids.nullprice}")
    @Positive(message = "{validation.bids.positiveprice}")
    private Double bidPrice;
}

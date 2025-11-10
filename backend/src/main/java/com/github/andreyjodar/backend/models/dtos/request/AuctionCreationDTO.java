package com.github.andreyjodar.backend.models.dtos.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuctionCreationDTO {
    @NotBlank(message = "{validation.auctions.blanktitle}")
    @Size(max = 100, message = "{validation.auctions.outsizetitle}")
    private String title;

    @NotBlank(message = "{validation.auctions.blankdesc}")
    @Size(max = 200, message = "{validation.auctions.outsizedesc}")
    private String description;

    private String expandedDescription;

    @NotNull(message = "{validation.auctions.nullcategoryid}")
    private Long categoryId;

    @NotNull(message = "{validation.auctions.nullstartdate}")
    private LocalDateTime startDateTime;

    @NotNull(message = "{validation.auctions.nullenddate}")
    private LocalDateTime endDateTime;

    @NotNull(message = "{validation.auctions.nullminbid}")
    @Positive(message = "{validation.auctions.positiveminbid}")
    private Float minBid;
}

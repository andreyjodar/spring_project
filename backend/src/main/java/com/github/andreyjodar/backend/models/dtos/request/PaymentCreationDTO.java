package com.github.andreyjodar.backend.models.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentCreationDTO {
    @NotNull(message = "{validation.payments.nullauctionid}")
    private Long auctionId;
}

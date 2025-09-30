package com.github.andreyjodar.backend.features.payment.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotNull
    private Long auctionId;
    @NotNull @Positive
    private Float value;
}

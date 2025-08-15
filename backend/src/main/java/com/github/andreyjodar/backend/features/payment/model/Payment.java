package com.github.andreyjodar.backend.features.payment.model;

import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.auction.model.Auction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payment")
public class Payment extends BaseEntity {

    @NotNull
    @OneToOne
    @JoinColumn(name="id_auction", nullable = false , unique = true)
    private Auction auction;

    @NotNull
    @Positive
    @Column(name = "value", nullable = false)
    private Float value;
    
    @NotBlank @Size(max = 100)
    @Column(name = "status", nullable = false, length = 100)
    private String status;
}

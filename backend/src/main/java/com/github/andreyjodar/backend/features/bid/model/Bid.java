package com.github.andreyjodar.backend.features.bid.model;

import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bid")
public class Bid extends BaseEntity {

    @NotNull
    @Positive
    @Column(name = "bid_value", nullable = false)
    private Float bidValue;

    @NotNull
    @ManyToOne 
    @JoinColumn(name = "id_bidder", nullable = false)
    private User bidder;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_auction", nullable = false)
    private Auction auction;
}

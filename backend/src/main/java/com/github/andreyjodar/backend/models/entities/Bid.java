package com.github.andreyjodar.backend.models.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.github.andreyjodar.backend.core.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bids")
public class Bid extends BaseEntity {

    @Column(name = "bid_price", nullable = false)
    private Double bidPrice;

    @NotNull @ManyToOne 
    @JoinColumn(name = "id_bidder", nullable = false)
    private User bidder;

    @NotNull @ManyToOne
    @JoinColumn(name = "id_auction", nullable = false)
    private Auction auction;
    
}

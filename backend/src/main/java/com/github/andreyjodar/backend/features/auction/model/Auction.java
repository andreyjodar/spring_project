package com.github.andreyjodar.backend.features.auction.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.payment.model.Payment;
import com.github.andreyjodar.backend.features.picture.Picture;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="auctions")
@SQLDelete(sql = "UPDATE auctions SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Auction extends BaseEntity {

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "expanded_description")
    private String expandedDescription;

    @NotNull @ManyToOne
    @JoinColumn(name="id_auctioneer", nullable = false)
    private User auctioneer; 

    @NotNull @ManyToOne
    @JoinColumn(name="id_category", nullable = false)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @NotNull @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AuctionStatus status;

    @Column(name = "deleted", nullable = false)
    @JsonIgnore
    private Boolean deleted = false;

    @Column(name = "increment_value", nullable = false)
    private Float incrementValue;

    @Column(name = "min_bid", nullable = false)
    private Float minBid;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Bid> bids;

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore 
    private Payment payment;
}

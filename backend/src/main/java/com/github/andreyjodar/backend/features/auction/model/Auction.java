package com.github.andreyjodar.backend.features.auction.model;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
@Table(name="auctions")
public class Auction extends BaseEntity {

    @NotBlank @Size(max = 100)
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NotBlank @Size(max = 200)
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

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDateTime;

    @NotNull
    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @NotNull @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AuctionStatus status;

    @Size(max = 150)
    @Column(name = "note", length = 150)
    private String note;

    @NotNull @PositiveOrZero
    @Column(name = "increment_value", nullable = false)
    private Float incrementValue;

    @NotNull @Positive
    @Column(name = "min_bid", nullable = false)
    private Float minBid;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids;

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    private Payment payment;
}

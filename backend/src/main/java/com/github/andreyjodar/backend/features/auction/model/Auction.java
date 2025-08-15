package com.github.andreyjodar.backend.features.auction.model;

import java.time.LocalDateTime;
import java.util.List;

import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.picture.Picture;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
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
@Table(name="auction")
public class Auction extends BaseEntity {

    @NotBlank @Size(max = 100)
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name="id_auctioneer", nullable = false)
    private User auctioneer; 

    @NotNull
    @ManyToOne
    @JoinColumn(name="id_category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "auction")
    private List<Picture> pictures;

    @NotBlank @Size(max = 200)
    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Size(max = 255)
    @Column(name = "expanded_description", nullable = true, length = 255)
    private String expandedDescription;

    @NotNull
    @FutureOrPresent
    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;

    @NotNull
    @FutureOrPresent
    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AuctionStatus status;

    @Size(max = 150)
    @Column(name = "note", nullable = true, length = 150)
    private String note;

    @NotNull
    @Min(value = 0)
    @Column(name = "increment_value", nullable = false)
    private Float incrementValue;

    @NotNull
    @Positive
    @Column(name = "min_bid", nullable = false)
    private Float minBid;
}



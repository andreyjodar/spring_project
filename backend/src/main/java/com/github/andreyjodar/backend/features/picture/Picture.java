package com.github.andreyjodar.backend.features.picture;

import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.auction.model.Auction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="picture")
public class Picture extends BaseEntity {

    @NotBlank @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name="id_auction", nullable = false)
    private Auction auction;
}

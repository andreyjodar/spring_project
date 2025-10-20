package com.github.andreyjodar.backend.models.entities;

import com.github.andreyjodar.backend.core.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="pictures")
public class Picture extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @NotNull @ManyToOne
    @JoinColumn(name="id_auction", nullable = false)
    private Auction auction;
}

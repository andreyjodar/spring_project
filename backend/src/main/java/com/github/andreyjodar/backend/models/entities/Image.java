package com.github.andreyjodar.backend.models.entities;

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
@Table(name="images")
public class Image extends BaseEntity {

    @Column(name = "unique_name", nullable = false)
    private String uniqueName;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @NotNull @ManyToOne
    @JoinColumn(name="id_auction", nullable = false)
    private Auction auction;
}

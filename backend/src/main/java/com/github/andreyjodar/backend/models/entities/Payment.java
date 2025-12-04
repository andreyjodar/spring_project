package com.github.andreyjodar.backend.models.entities;

import com.github.andreyjodar.backend.core.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
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
@Table(name="payments")
public class Payment extends BaseEntity {

    @NotNull @OneToOne
    @JoinColumn(name="id_auction", nullable = false)
    private Auction auction;

    @NotNull @Positive
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull @ManyToOne
    @JoinColumn(name="id_buyer", nullable = false)
    private User buyer;
    
    @NotBlank @Size(max = 100)
    @Column(name = "status", nullable = false, length = 100)
    private String status;
}

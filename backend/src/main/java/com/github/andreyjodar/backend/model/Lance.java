package com.github.andreyjodar.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data 
@Table(name = "lance")
public class Lance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{validation.bidvalue.notblank}")
    private Double valorLance;
    @NotBlank(message = "{validation.datetime.notblank}")
    private LocalDateTime dataHora;
    @ManyToOne 
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;
}

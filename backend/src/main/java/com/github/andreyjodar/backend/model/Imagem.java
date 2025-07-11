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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="imagem")
public class Imagem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.name.notblank}")
    @Size(max = 100, message = "{validation.name.size}")
    private String nomeImagem;

    @NotNull(message = "{validation.datetime.notnull}")
    @PastOrPresent(message = "{validation.datetime.notfuture}")
    private LocalDateTime dataHoraCadastro;

    @ManyToOne
    @JoinColumn(name="leilao_id", nullable = false)
    private Leilao leilao;
}

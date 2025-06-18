package com.github.andreyjodar.backend.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name="leilao")
public class Leilao {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.title.notblank}")
    private String titulo;

    @ManyToOne
    @JoinColumn(name="autor_id", nullable = false)
    private Pessoa autor; 

    @ManyToOne
    @JoinColumn(name="categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "leilao")
    private List<Imagem> imagens;

    @NotBlank(message = "{validation.description.notblank}")
    private String descricao;

    @NotBlank(message = "{validation.longdescription.notblank}")
    private String descricaoDetalhada;

    @NotNull(message = "{validation.startdatetime.notnull}")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "{validation.enddatetime.notnull}")
    private LocalDateTime dataHoraFim;

    @NotNull(message = "{validation.status.notnull}")
    @Enumerated(EnumType.STRING)
    private StatusLeilao status;

    @NotBlank(message = "{validation.note.notblank}")
    private String observacao;

    @NotNull(message = "{validation.incrementvalue.notnull}")
    private Float valorIncremento;

    @NotNull(message = "{validation.minbid.notnull}")
    private Float lanceMinimo;
}

enum StatusLeilao {
    ABERTO, ENCERRADO, CANCELADO, EM_ANALISE
} 

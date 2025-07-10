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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="leilao")
public class Leilao {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.title.notblank}")
    @Size(max = 100, message = "{validation.title.size}")
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
    @Size(max = 255, message = "{validation.description.size}")
    private String descricao;

    @NotBlank(message = "{validation.longdescription.notblank}")
    @Size(max = 255, message = "{validation.longdescription.size}")
    private String descricaoDetalhada;

    @NotNull(message = "{validation.datetime.notnull}")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "{validation.datetime.notnull}")
    private LocalDateTime dataHoraFim;

    @NotNull(message = "{validation.status.notnull}")
    @Enumerated(EnumType.STRING)
    private StatusLeilao status;

    @NotBlank(message = "{validation.note.notblank}")
    @Size(max = 200, message = "{validation.note.size}")
    private String observacao;

    @NotNull(message = "{validation.incrementvalue.notnull}")
    @Positive(message = "{validation.incrementvalue.positive}")
    private Float valorIncremento;

    @NotNull(message = "{validation.minbid.notnull}")
    @Positive(message = "{validation.minbid.positive}")
    private Float lanceMinimo;
}

enum StatusLeilao {
    ABERTO, ENCERRADO, CANCELADO, EM_ANALISE
} 

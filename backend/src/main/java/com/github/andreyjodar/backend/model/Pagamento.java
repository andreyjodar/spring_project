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
import lombok.Data;

@Entity
@Data
@Table(name="pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="leilao_id", nullable = false)
    private Leilao leilao;

    @NotNull(message = "{validation.value.notnull}")
    private Float valor;

    @NotNull(message = "{validation.datetime.notnull}")
    private LocalDateTime dataHora;
    
    @NotBlank(message = "{validation.status.notblank}")
    private String status;
}

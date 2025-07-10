package com.github.andreyjodar.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="leilao_id", unique=true)
    private Leilao leilao;

    @NotNull(message = "{validation.value.notnull}")
    @Positive(message = "{validation.value.positive}")
    private Float valor;

    @NotNull(message = "{validation.datetime.notnull}")
    private LocalDateTime dataHora;
    
    @NotBlank(message = "{validation.status.notblank}")
    @Size(max = 100, message = "validation.status.size")
    private String status;
}

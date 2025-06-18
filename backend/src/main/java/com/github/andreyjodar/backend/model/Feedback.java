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
@Table(name="feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{validation.grade.notnull}")
    private Integer nota;

    @NotBlank(message = "{validation.comment.notblank}")
    private String comentario;

    @NotNull(message = "{validation.datetime.notnull}")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name="autor_id", nullable=false)
    private Pessoa autor;
    
    @ManyToOne
    @JoinColumn(name="destinatario_id", nullable=false)
    private Pessoa destinatario;
}

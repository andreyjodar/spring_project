package com.github.andreyjodar.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.name.notblank}")
    @Size(max = 100, message = "{validation.name.size}")
    private String nome;

    @NotBlank(message = "{validation.note.notblank}")
    @Size(max = 200, message = "{validation.note.size}")
    private String observacao;
    
    @ManyToOne
    @JoinColumn(name="autor_id", nullable = false)
    private Pessoa autor;
}

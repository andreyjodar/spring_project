package com.github.andreyjodar.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name="perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{validation.type.notnull}")
    @Enumerated(EnumType.STRING)
    private TipoPerfil tipo;
    
}

enum TipoPerfil {
    ADMIN, COMPRADOR, VENDEDOR
}

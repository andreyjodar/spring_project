package com.github.andreyjodar.backend.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.name.notblank}")
    private String nome;

    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.notvalid}")
    private String email;

    @NotBlank(message = "{validation.password.notblank}")
    private String senha;

    @NotBlank(message = "{validation.validatorcode.notblank}")
    private String codigoValidacao;

    @NotNull(message = "{validation.validatorcodelimit.notnull}")
    private LocalDate validadeCodigoValidacao;

    @NotNull(message = "{validation.active.notnull}")
    private Boolean ativo;

    @OneToMany(mappedBy = "autor")
    private List<Feedback> feedbacksEscritos;

    @OneToMany(mappedBy = "destinatario")
    private List<Feedback> feedbacksRecebidos;

    @OneToMany(mappedBy = "autor")
    private List<Leilao> meusLeiloes;

    @OneToMany(mappedBy = "autor")
    private List<Categoria> minhasCategorias;

    @Lob
    @Column(name = "foto_perfil", columnDefinition = "LONGBLOB")
    private byte[] fotoPerfil;
}

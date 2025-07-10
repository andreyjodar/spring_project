package com.github.andreyjodar.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.name.notblank}")
    @Size(max = 100, message = "{validation.name.size}")
    private String nome;

    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.notvalid}")
    private String email;

    @NotBlank(message = "{validation.password.notblank}")
    @Size(min = 6, message = "{validation.password.size}")
    private String senha;

    @NotBlank(message = "{validation.validatorcode.notblank}")
    @Size(max = 100, message = "{validation.validatorcode.size}")
    private String codigoValidacao;

    @NotNull(message = "{validation.date.notnull}")
    private LocalDate validadeCodigoValidacao;

    @NotNull(message = "{validation.active.notnull}")
    private Boolean ativo;

    @OneToMany(mappedBy = "destinatario")
    private List<Feedback> feedbacksRecebidos;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaPerfil> pessoaPerfis = new ArrayList<>();

    @Lob
    @Column(name = "foto_perfil", columnDefinition = "LONGBLOB")
    private byte[] fotoPerfil;
}

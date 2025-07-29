package com.github.andreyjodar.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@Table(name = "pessoa")
public class Pessoa implements UserDetails {
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
    @JsonIgnore
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

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Setter(value = AccessLevel.NONE)
    private List<PessoaPerfil> pessoaPerfil = new ArrayList<>();

    @Lob
    @Column(name = "foto_perfil", columnDefinition = "LONGBLOB")
    private byte[] fotoPerfil;

    public void setPessoaPerfil(List<PessoaPerfil> pessoaPerfil) {
        for (PessoaPerfil p : pessoaPerfil) {
            p.setPessoa(this);
        }
        this.pessoaPerfil = pessoaPerfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return pessoaPerfil.stream().map(user -> new SimpleGrantedAuthority(user.getPerfil().getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}

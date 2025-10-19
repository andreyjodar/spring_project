package com.github.andreyjodar.backend.features.user.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.stream.Collectors;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.role.model.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false") 
public class User extends BaseEntity implements UserDetails {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
 
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "validity_code", length = 6)
    @JsonIgnore
    private String validityCode;

    @Column(name = "expiration_date")
    @JsonIgnore
    private LocalDateTime expirationDate;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "deleted", nullable = false)
    @JsonIgnore
    private Boolean deleted = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    public Boolean isAdmin() {
        return roles.stream().anyMatch(role -> role.getType().equals("ADMIN"));
    }

    @JsonIgnore
    public Boolean isSeller() {
        return roles.stream().anyMatch(role -> role.getType().equals("SELLER"));
    }

    @JsonIgnore
    public Boolean isBuyer() {
        return roles.stream().anyMatch(role -> role.getType().equals("BUYER"));
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getType()))
            .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return active;
    }
}

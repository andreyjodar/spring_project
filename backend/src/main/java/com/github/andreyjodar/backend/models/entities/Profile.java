package com.github.andreyjodar.backend.models.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.andreyjodar.backend.core.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@SQLDelete(sql = "UPDATE profiles SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false") 
@Table(name = "profiles")
public class Profile extends BaseEntity {

    @Column(name = "role", unique = true, nullable = false)
    private String role;

    @Column(name = "deleted", nullable = false)
    @JsonIgnore
    private boolean deleted = false;

    public Profile(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}

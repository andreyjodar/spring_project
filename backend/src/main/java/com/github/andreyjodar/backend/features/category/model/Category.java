package com.github.andreyjodar.backend.features.category.model;

import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "note", nullable = false, length = 150)
    private String note;
    
    @NotNull @ManyToOne
    @JoinColumn(name="id_author", nullable = false)
    private User author;
}

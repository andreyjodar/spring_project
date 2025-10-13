package com.github.andreyjodar.backend.features.category.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.andreyjodar.backend.core.model.BaseEntity;

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
@Table(name="categories")
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false") 
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "note", nullable = false, length = 150)
    private String note;

    @Column(name = "deleted", nullable = false)
    @JsonIgnore
    private Boolean deleted = false;
}

package com.github.andreyjodar.backend.features.feedback.model;

import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="feedback")
public class Feedback extends BaseEntity {

    @NotNull
    @Min(value = 1) @Max(value = 5)
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @NotNull @Size(max = 255)
    @Column(name = "comment", nullable = false, length = 255)
    private String comment;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_author", nullable = false)
    private User author;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_recipient", nullable = false)
    private User recipient;
}

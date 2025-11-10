package com.github.andreyjodar.backend.models.entities;

import com.github.andreyjodar.backend.core.models.BaseEntity;

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
@Table(name="feedbacks")
public class Feedback extends BaseEntity {

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "comment", nullable = false, length = 255)
    private String comment;

    @NotNull @ManyToOne
    @JoinColumn(name = "id_auction", nullable = false)
    private Auction auction;

    @NotNull @ManyToOne
    @JoinColumn(name = "id_author", nullable = false)
    private User author;
}

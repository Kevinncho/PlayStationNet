package com.kefessan.playstationet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGame;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;
    private String coverImage;
    private LocalDate releaseDate;

    // Relación con Categorías
    @ManyToMany
    @JoinTable(
        name = "GameCategory",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    // Relación con Géneros
    @ManyToMany
    @JoinTable(
        name = "GameGenre",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;
}
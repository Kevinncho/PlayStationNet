package com.kefessan.playstationet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class GameCreateDTO {
    
    @NotBlank
    private String title;
    private String description;
    private BigDecimal price;
    private String coverImage;
    private LocalDate releaseDate;
    // cuando se crea un juego se pueden enviar los ids de categorías/ géneros
    private Set<Long> categoryIds;
    private Set<Long> genreIds;
}    

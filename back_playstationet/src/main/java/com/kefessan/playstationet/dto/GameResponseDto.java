package com.kefessan.playstationet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameResponseDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private String coverImage;
    private LocalDate releaseDate;

    // Solo nombres, NO entidades
    private Set<String> categories;
    private Set<String> genres;
}
package com.kefessan.playstationet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO {

    private Long idCategory;
    private String name;

    // nombres de juegos asociados (opcional, para mostrar relación)
    private Set<String> games;
}

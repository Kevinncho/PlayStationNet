package com.kefessan.playstationet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kefessan.playstationet.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
    // permite filtrar por categorías usando el id de la categoría
    Page<Game> findAllByCategories_IdCategory(Long categoryId, Pageable pageable);
}

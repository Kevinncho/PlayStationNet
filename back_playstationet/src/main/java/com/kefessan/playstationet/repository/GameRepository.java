package com.kefessan.playstationet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kefessan.playstationet.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}

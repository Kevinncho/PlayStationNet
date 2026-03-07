package com.kefessan.playstationet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.dto.GameCreateDTO;
import com.kefessan.playstationet.dto.GameUpdateDTO;
import com.kefessan.playstationet.model.Game;
import com.kefessan.playstationet.service.GameService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/games")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class GameAdminController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Game> createGame(
            @Valid @RequestBody GameCreateDTO dto) {

        Game game = gameService.createGame(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(
            @PathVariable Long id,
            @Valid @RequestBody GameUpdateDTO dto) {

        Game updatedGame = gameService.updateGame(id, dto);
        return ResponseEntity.ok(updatedGame);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
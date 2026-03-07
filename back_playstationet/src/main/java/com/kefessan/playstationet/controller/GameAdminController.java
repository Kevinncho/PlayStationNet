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
import com.kefessan.playstationet.dto.GameResponseDto;
import com.kefessan.playstationet.dto.GameUpdateDTO;
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
    public ResponseEntity<GameResponseDto> createGame(
            @Valid @RequestBody GameCreateDTO dto) {

        GameResponseDto game = gameService.createGame(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDto> updateGame(
            @PathVariable Long id,
            @Valid @RequestBody GameUpdateDTO dto) {

        GameResponseDto updatedGame = gameService.updateGame(id, dto);
        return ResponseEntity.ok(updatedGame);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
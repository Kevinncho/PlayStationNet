package com.kefessan.playstationet.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.dto.GameResponseDto;
import com.kefessan.playstationet.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

   // 👉 GET /games
    // 👉 GET /games?page=0&size=5
    @GetMapping
    public Page<GameResponseDto> getGames(Pageable pageable) {
        return gameService.getGames(pageable);
    }
}
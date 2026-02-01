package com.kefessan.playstationet.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kefessan.playstationet.dto.GameResponseDto;
import com.kefessan.playstationet.mapper.GameMapper;
import com.kefessan.playstationet.repository.GameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public Page<GameResponseDto> getGames(Pageable pageable) {
    return gameRepository.findAll(pageable)
            .map(gameMapper::toResponseDto);
}

}

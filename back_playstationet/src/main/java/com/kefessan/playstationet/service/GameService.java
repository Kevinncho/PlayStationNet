package com.kefessan.playstationet.service;

import java.util.HashSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kefessan.playstationet.dto.GameCreateDTO;
import com.kefessan.playstationet.dto.GameResponseDto;
import com.kefessan.playstationet.dto.GameUpdateDTO;
import com.kefessan.playstationet.mapper.GameMapper;
import com.kefessan.playstationet.model.Game;
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
      public Game createGame(GameCreateDTO dto) {

        Game game = Game.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .coverImage(dto.getCoverImage())
                .releaseDate(dto.getReleaseDate())
                .build();

        return gameRepository.save(game);
    }
  public Game updateGame(Long id, GameUpdateDTO dto) {

        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setCoverImage(dto.getCoverImage());
        game.setReleaseDate(dto.getReleaseDate());

        return gameRepository.save(game);
    }

        public GameResponseDto getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));

        return gameMapper.toResponseDto(game);
        }
        public void deleteGame(Long id) {

        Game game = gameRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));

        gameRepository.delete(game);
        }
}

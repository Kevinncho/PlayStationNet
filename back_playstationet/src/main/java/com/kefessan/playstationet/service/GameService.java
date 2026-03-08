package com.kefessan.playstationet.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kefessan.playstationet.dto.GameCreateDTO;
import com.kefessan.playstationet.dto.GameResponseDto;
import com.kefessan.playstationet.dto.GameUpdateDTO;
import com.kefessan.playstationet.mapper.GameMapper;
import com.kefessan.playstationet.model.Category;
import com.kefessan.playstationet.model.Game;
import com.kefessan.playstationet.model.Genre;
import com.kefessan.playstationet.repository.CategoryRepository;
import com.kefessan.playstationet.repository.GameRepository;
import com.kefessan.playstationet.repository.GenreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;
    private final GameMapper gameMapper;

    public Page<GameResponseDto> getGames(Pageable pageable) {
        return gameRepository.findAll(pageable)
                .map(gameMapper::toResponseDto);
    }

    // filtro opcional por categoría
    public Page<GameResponseDto> getGamesByCategory(Long categoryId, Pageable pageable) {
        return gameRepository.findAllByCategories_IdCategory(categoryId, pageable)
                .map(gameMapper::toResponseDto);
    }

    public GameResponseDto createGame(GameCreateDTO dto) {

        Game game = Game.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .coverImage(dto.getCoverImage())
                .releaseDate(dto.getReleaseDate())
                .build();

        if (dto.getCategoryIds() != null) {
            Set<Category> cats = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            game.setCategories(cats);
        }
        if (dto.getGenreIds() != null) {
            Set<Genre> gens = new HashSet<>(genreRepository.findAllById(dto.getGenreIds()));
            game.setGenres(gens);
        }

        Game savedGame = gameRepository.save(game);
        return gameMapper.toResponseDto(savedGame);
    }

    public GameResponseDto updateGame(Long id, GameUpdateDTO dto) {

        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (dto.getTitle() != null) {
            game.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            game.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            game.setPrice(dto.getPrice());
        }
        if (dto.getCoverImage() != null) {
            game.setCoverImage(dto.getCoverImage());
        }
        if (dto.getReleaseDate() != null) {
            game.setReleaseDate(dto.getReleaseDate());
        }

        if (dto.getCategoryIds() != null) {
            Set<Category> cats = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            game.setCategories(cats);
        }
        if (dto.getGenreIds() != null) {
            Set<Genre> gens = new HashSet<>(genreRepository.findAllById(dto.getGenreIds()));
            game.setGenres(gens);
        }

        Game updatedGame = gameRepository.save(game);
        return gameMapper.toResponseDto(updatedGame);
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

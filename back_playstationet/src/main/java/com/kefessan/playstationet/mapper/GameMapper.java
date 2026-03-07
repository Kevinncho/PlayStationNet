package com.kefessan.playstationet.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.kefessan.playstationet.dto.GameResponseDto;
import com.kefessan.playstationet.model.Game;

    @Component
public class GameMapper {

  public GameResponseDto toResponseDto(Game game) {
    return GameResponseDto.builder()
            .id(game.getIdGame())
            .title(game.getTitle())
            .price(game.getPrice())
            .coverImage(game.getCoverImage())
            .releaseDate(game.getReleaseDate())
            .categories(
                game.getCategories()
                    .stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toSet())
            )
            .genres(
                game.getGenres()
                    .stream()
                    .map(g -> g.getName())
                    .collect(Collectors.toSet())
            )
            .build();
}

}

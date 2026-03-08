package com.kefessan.playstationet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.model.Genre;
import com.kefessan.playstationet.service.GenreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/genres")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class GenreAdminController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        Genre createdGenre = genreService.createGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(
            @PathVariable Long id,
            @RequestBody Genre updatedGenre) {
        return ResponseEntity.ok(genreService.updateGenre(id, updatedGenre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}

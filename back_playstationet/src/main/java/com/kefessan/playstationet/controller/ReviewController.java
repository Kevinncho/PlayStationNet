package com.kefessan.playstationet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.dto.ReviewCreateDTO;
import com.kefessan.playstationet.dto.ReviewResponseDto;
import com.kefessan.playstationet.dto.ReviewUpdateDTO;
import com.kefessan.playstationet.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponseDto createReview(
            @RequestParam Long userId,
            @RequestParam Long gameId,
            @RequestBody ReviewCreateDTO review) {

        return reviewService.createReview(userId, gameId, review);
    }

    @GetMapping("/game/{gameId}")
    public List<ReviewResponseDto> getReviewsByGame(@PathVariable Long gameId) {
        return reviewService.getReviewsByGame(gameId);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewResponseDto> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @PutMapping("/{id}")
    public ReviewResponseDto updateReview(@PathVariable Long id,
                                          @RequestBody ReviewUpdateDTO review) {

        return reviewService.updateReview(id, review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}

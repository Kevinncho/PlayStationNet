
package com.kefessan.playstationet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kefessan.playstationet.dto.ReviewCreateDTO;
import com.kefessan.playstationet.dto.ReviewResponseDto;
import com.kefessan.playstationet.dto.ReviewUpdateDTO;
import com.kefessan.playstationet.mapper.ReviewMapper;
import com.kefessan.playstationet.model.Game;
import com.kefessan.playstationet.model.Review;
import com.kefessan.playstationet.model.User;
import com.kefessan.playstationet.repository.GameRepository;
import com.kefessan.playstationet.repository.ReviewRepository;
import com.kefessan.playstationet.repository.UserRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         GameRepository gameRepository,
                         ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.reviewMapper = reviewMapper;
    }

    public ReviewResponseDto createReview(Long userId, Long gameId, ReviewCreateDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Review review = new Review();
        review.setUser(user);
        review.setGame(game);
        review.setRating(dto.getRating());
        review.setContent(dto.getContent());
        review.setCreatedAt(LocalDateTime.now());

        return reviewMapper.toResponseDto(reviewRepository.save(review));
    }

    public List<ReviewResponseDto> getReviewsByGame(Long gameId) {
        return reviewRepository.findByGame_IdGame(gameId)
                .stream()
                .map(reviewMapper::toResponseDto)
                .toList();
    }

    public List<ReviewResponseDto> getReviewsByUser(Long userId) {
        return reviewRepository.findByUser_IdUser(userId)
                .stream()
                .map(reviewMapper::toResponseDto)
                .toList();
    }

    public ReviewResponseDto updateReview(Long id, ReviewUpdateDTO dto) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (dto.getRating() != null) {
            review.setRating(dto.getRating());
        }
        if (dto.getContent() != null) {
            review.setContent(dto.getContent());
        }

        return reviewMapper.toResponseDto(reviewRepository.save(review));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}

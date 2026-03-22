package com.kefessan.playstationet.mapper;

import org.springframework.stereotype.Component;

import com.kefessan.playstationet.dto.ReviewResponseDto;
import com.kefessan.playstationet.model.Review;

@Component
public class ReviewMapper {

    public ReviewResponseDto toResponseDto(Review review) {
        return ReviewResponseDto.builder()
                .idReview(review.getIdReview())
                .userId(review.getUser() != null ? review.getUser().getIdUser() : null)
                .username(review.getUser() != null ? review.getUser().getUsername() : null)
                .gameId(review.getGame() != null ? review.getGame().getIdGame() : null)
                .gameTitle(review.getGame() != null ? review.getGame().getTitle() : null)
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

package com.kefessan.playstationet.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {

    private Long idReview;
    private Long userId;
    private String username;
    private Long gameId;
    private String gameTitle;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
}

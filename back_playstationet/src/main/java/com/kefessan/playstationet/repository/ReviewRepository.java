package com.kefessan.playstationet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kefessan.playstationet.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGame_IdGame(Long gameId);

    List<Review> findByUser_IdUser(Long userId);
    
    boolean existsByUser_IdUserAndGame_IdGame(Long userId, Long gameId);
    
}

package com.kefessan.playstationet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kefessan.playstationet.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "items", "items.game"})
    List<Order> findAllByUser_IdUserOrderByDateCreatedDesc(Long userId);

    @EntityGraph(attributePaths = {"user", "items", "items.game"})
    Optional<Order> findByIdOrder(Long idOrder);
}

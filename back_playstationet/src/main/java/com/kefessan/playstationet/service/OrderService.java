package com.kefessan.playstationet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.kefessan.playstationet.dto.OrderCreateDTO;
import com.kefessan.playstationet.dto.OrderResponseDto;
import com.kefessan.playstationet.dto.OrderUpdateDTO;
import com.kefessan.playstationet.mapper.OrderMapper;
import com.kefessan.playstationet.model.Game;
import com.kefessan.playstationet.model.Order;
import com.kefessan.playstationet.model.OrderItem;
import com.kefessan.playstationet.model.User;
import com.kefessan.playstationet.repository.GameRepository;
import com.kefessan.playstationet.repository.OrderRepository;
import com.kefessan.playstationet.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponseDto createOrder(String username, OrderCreateDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Game> games = gameRepository.findAllById(dto.getGameIds());
        if (games.size() != dto.getGameIds().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more games were not found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setDateCreated(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderItem> items = games.stream()
                .map(game -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setGame(game);
                    item.setPriceAtPurchase(game.getPrice());
                    return item;
                })
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        BigDecimal totalAmount = items.stream()
                .map(OrderItem::getPriceAtPurchase)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setItems(items);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return orderRepository.findAllByUser_IdUserOrderByDateCreatedDesc(user.getIdUser())
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId, String username, boolean isAdmin) {
        Order order = getAuthorizedOrder(orderId, username, isAdmin);

        return orderMapper.toResponseDto(order);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long orderId, String username, boolean isAdmin, OrderUpdateDTO dto) {
        Order order = getAuthorizedOrder(orderId, username, isAdmin);

        if (dto.getStatus() != null) {
            order.setStatus(dto.getStatus());
        }

        if (dto.getGameIds() != null) {
            if (dto.getGameIds().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "gameIds cannot be empty");
            }

            List<Game> games = gameRepository.findAllById(dto.getGameIds());
            if (games.size() != dto.getGameIds().size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more games were not found");
            }

            List<OrderItem> items = games.stream()
                    .map(game -> {
                        OrderItem item = new OrderItem();
                        item.setOrder(order);
                        item.setGame(game);
                        item.setPriceAtPurchase(game.getPrice());
                        return item;
                    })
                    .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

            BigDecimal totalAmount = items.stream()
                    .map(OrderItem::getPriceAtPurchase)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            order.getItems().clear();
            order.getItems().addAll(items);
            order.setTotalAmount(totalAmount);
        }

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponseDto(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long orderId, String username, boolean isAdmin) {
        Order order = getAuthorizedOrder(orderId, username, isAdmin);
        orderRepository.delete(order);
    }

    private Order getAuthorizedOrder(Long orderId, String username, boolean isAdmin) {
        Order order = orderRepository.findByIdOrder(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (!isAdmin && !order.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this order");
        }

        return order;
    }
}

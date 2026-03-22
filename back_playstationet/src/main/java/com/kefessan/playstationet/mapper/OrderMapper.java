package com.kefessan.playstationet.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kefessan.playstationet.dto.OrderItemResponseDto;
import com.kefessan.playstationet.dto.OrderResponseDto;
import com.kefessan.playstationet.model.Order;
import com.kefessan.playstationet.model.OrderItem;

@Component
public class OrderMapper {

    public OrderResponseDto toResponseDto(Order order) {
        List<OrderItemResponseDto> items = order.getItems() == null
                ? List.of()
                : order.getItems().stream()
                        .map(this::toItemResponseDto)
                        .toList();

        return OrderResponseDto.builder()
                .idOrder(order.getIdOrder())
                .userId(order.getUser() != null ? order.getUser().getIdUser() : null)
                .username(order.getUser() != null ? order.getUser().getUsername() : null)
                .dateCreated(order.getDateCreated())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .items(items)
                .build();
    }

    private OrderItemResponseDto toItemResponseDto(OrderItem item) {
        return OrderItemResponseDto.builder()
                .idOrderItem(item.getIdOrderItem())
                .gameId(item.getGame() != null ? item.getGame().getIdGame() : null)
                .gameTitle(item.getGame() != null ? item.getGame().getTitle() : null)
                .priceAtPurchase(item.getPriceAtPurchase())
                .build();
    }
}

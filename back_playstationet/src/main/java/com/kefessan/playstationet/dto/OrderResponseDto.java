package com.kefessan.playstationet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {

    private Long idOrder;
    private Long userId;
    private String username;
    private LocalDateTime dateCreated;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemResponseDto> items;
}

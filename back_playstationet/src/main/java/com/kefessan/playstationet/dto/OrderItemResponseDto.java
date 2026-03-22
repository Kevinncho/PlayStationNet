package com.kefessan.playstationet.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemResponseDto {

    private Long idOrderItem;
    private Long gameId;
    private String gameTitle;
    private BigDecimal priceAtPurchase;
}

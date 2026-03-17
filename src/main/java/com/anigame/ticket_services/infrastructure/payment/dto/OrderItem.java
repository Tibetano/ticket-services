package com.anigame.ticket_services.infrastructure.payment.dto;

import com.anigame.ticket_services.domain.enums.ItemType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItem(
        UUID id,
        ItemType type,
        Integer unitPrice,
        Integer quantity
) {
}

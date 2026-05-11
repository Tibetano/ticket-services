package com.anigame.ticket_services.infrastructure.payment.dto;

import com.anigame.ticket_services.domain.enums.TicketBatchTypeEnumEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItem(
        UUID id,
        TicketBatchTypeEnumEntity type,
        Integer unitPrice,
        Integer quantity
) {
}

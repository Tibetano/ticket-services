package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.OrderStatusEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderResponseDTO(
        UUID orderId,
        OrderStatusEntity status,
        PaymentResponseDTO payment
) {
}

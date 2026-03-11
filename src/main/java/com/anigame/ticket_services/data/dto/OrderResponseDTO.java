package com.anigame.ticket_services.data.dto;

import com.anigame.ticket_services.z_domain.OrderStatusEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderResponseDTO(
        UUID orderId,
        OrderStatusEntity status,
        PaymentResponseDTO payment
) {
}

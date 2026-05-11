package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.order.OrderStatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record OrderResponseDTO(
        @JsonProperty("order_number")
        Long orderNumber,
        OrderStatusEntity status,
        PaymentResponseDTO payment
) {
}

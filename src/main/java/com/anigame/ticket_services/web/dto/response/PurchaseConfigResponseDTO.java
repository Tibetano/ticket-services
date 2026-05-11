package com.anigame.ticket_services.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseConfigResponseDTO(
        @JsonProperty("max_tickets_per_user")
        Long maxTicketsPerUser,
        @JsonProperty("payment_methods")
        List<PaymentMethodResponseDTO> paymentMethods
) {
}

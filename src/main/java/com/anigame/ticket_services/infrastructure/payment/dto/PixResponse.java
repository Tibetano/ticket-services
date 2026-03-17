package com.anigame.ticket_services.infrastructure.payment.dto;

import lombok.Builder;

@Builder
public record PixResponse(
        String txId,
        String locId
) {
}

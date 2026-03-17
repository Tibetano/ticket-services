package com.anigame.ticket_services.infrastructure.payment.dto;

import lombok.Builder;

@Builder
public record CardResponse(
        Long chargeId
) {
}

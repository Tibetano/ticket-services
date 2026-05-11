package com.anigame.ticket_services.web.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FeeDTO(
        String type,
        BigDecimal value
) {
}

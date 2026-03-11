package com.anigame.ticket_services.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketRequestDTO(
        @NotNull
        String type,
        @Positive
        Integer quantity
) {
}

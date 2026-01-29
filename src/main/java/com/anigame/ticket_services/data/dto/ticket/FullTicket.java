package com.anigame.ticket_services.data.dto.ticket;

public record FullTicket(
        Integer price,
        Integer totalQuantity,
        Integer soldQuantity
) {
}

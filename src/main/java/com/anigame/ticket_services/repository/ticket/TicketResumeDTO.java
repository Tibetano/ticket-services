package com.anigame.ticket_services.repository.ticket;

public record TicketResumeDTO(
        String id,
        String qrCodeHash,
        String ticketType,
        Integer year
) {
}

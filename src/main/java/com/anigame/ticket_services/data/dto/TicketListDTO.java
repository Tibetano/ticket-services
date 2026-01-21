package com.anigame.ticket_services.data.dto;

import java.util.List;

public record TicketListDTO(
        List<Ticket2DTO> ticketList
) {
}

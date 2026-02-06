package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.model.database.Ticket;

public interface TicketRepository {
    void save(Ticket ticket);
}

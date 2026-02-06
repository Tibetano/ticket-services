package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.model.database.TicketBatchType;

public interface TicketBatchTypeRepository {
    void save(TicketBatchType ticketBatchType);
}

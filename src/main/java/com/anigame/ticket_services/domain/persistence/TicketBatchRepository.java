package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.model.database.TicketBatch;

public interface TicketBatchRepository {
    void save(TicketBatch ticketBatch);
}

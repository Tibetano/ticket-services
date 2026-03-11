package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.model.old_model.database.TicketBatchType;
import com.anigame.ticket_services.domain.model.old_model.database.TicketBatchTypeIdPrice;
import com.anigame.ticket_services.domain.model.old_model.database.enumerate.TicketBatchTypeEnum;

import java.util.Optional;
import java.util.UUID;

public interface TicketBatchTypeRepository {
    void save(TicketBatchType ticketBatchType);
    Optional<TicketBatchTypeIdPrice> reserveTickets (UUID ticketBatchId, TicketBatchTypeEnum ticketType, Integer quantity);
}

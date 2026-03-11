package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.z_domain.TicketBatchEntity;

import java.util.Optional;
import java.util.UUID;

public interface TicketBatchRepository {
    Optional<TicketBatchEntity> findById(UUID id);
    UUID save(TicketBatchEntity ticketBatchEntity);
}

package com.anigame.ticket_services.infrastructure.persistence.ticket_batch_type;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTicketBatchTypeRepository extends JpaRepository<TicketBatchTypeEntity, UUID> {
}

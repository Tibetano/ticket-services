package com.anigame.ticket_services.infrastructure.persistence.ticket_batch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTicketBatchRepository extends JpaRepository<TicketBatchEntity, UUID> {
}

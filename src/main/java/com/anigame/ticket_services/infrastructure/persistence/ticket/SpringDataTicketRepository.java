package com.anigame.ticket_services.infrastructure.persistence.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTicketRepository extends JpaRepository<TicketEntity, UUID> {
}

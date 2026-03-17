package com.anigame.ticket_services.repository.ticket;

import com.anigame.ticket_services.domain.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTicketRepository extends JpaRepository<TicketEntity, UUID> {
}

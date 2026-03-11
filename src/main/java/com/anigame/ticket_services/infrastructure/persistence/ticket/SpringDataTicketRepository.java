package com.anigame.ticket_services.infrastructure.persistence.ticket;

import com.anigame.ticket_services.z_domain.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTicketRepository extends JpaRepository<TicketEntity, UUID> {
}

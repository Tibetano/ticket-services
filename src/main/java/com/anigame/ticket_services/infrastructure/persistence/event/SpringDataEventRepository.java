package com.anigame.ticket_services.infrastructure.persistence.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataEventRepository extends JpaRepository<EventEntity, UUID> {
}

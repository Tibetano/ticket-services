package com.anigame.ticket_services.repository.event;

import com.anigame.ticket_services.domain.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataEventRepository extends JpaRepository<EventEntity, UUID> {
}

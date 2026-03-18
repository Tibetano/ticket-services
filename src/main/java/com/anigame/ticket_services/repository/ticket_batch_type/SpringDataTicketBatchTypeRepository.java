package com.anigame.ticket_services.repository.ticket_batch_type;

import com.anigame.ticket_services.domain.TicketBatchTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataTicketBatchTypeRepository extends JpaRepository<TicketBatchTypeEntity, UUID> {
    List<TicketBatchTypeEntity> findByIdIn (List<UUID> idList);
}

package com.anigame.ticket_services.repository.ticket_batch;

import com.anigame.ticket_services.domain.TicketBatchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketBatchRepository {

    private final SpringDataTicketBatchRepository jpaRepo;

    public Optional<TicketBatchEntity> findById(UUID id) {
        return jpaRepo.findById(id);
    }

    public UUID save(TicketBatchEntity ticketBatchEntity) {
        var savedTicketBatch = jpaRepo.save(ticketBatchEntity);
        return savedTicketBatch.getId();
    }
}

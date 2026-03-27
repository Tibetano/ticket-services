package com.anigame.ticket_services.repository.ticket_batch;

import com.anigame.ticket_services.domain.TicketBatchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketBatchRepository {

    private final SpringDataTicketBatchRepository jpaRepo;

    public TicketBatchEntity findById(UUID id) {

        return jpaRepo.findById(id).orElseThrow(()->new RuntimeException("Ticket batch not found."));
    }

    public TicketBatchEntity findActiveBatch() {
        return jpaRepo.findActiveBatch().orElseThrow(() -> new RuntimeException("Corrent active ticket batch not found."));
    }

    public UUID save(TicketBatchEntity ticketBatchEntity) {
        var savedTicketBatch = jpaRepo.save(ticketBatchEntity);
        return savedTicketBatch.getId();
    }
}

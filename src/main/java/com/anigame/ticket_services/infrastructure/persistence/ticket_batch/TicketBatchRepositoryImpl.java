package com.anigame.ticket_services.infrastructure.persistence.ticket_batch;

import com.anigame.ticket_services.z_domain.TicketBatchEntity;
import com.anigame.ticket_services.domain.persistence.TicketBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketBatchRepositoryImpl implements TicketBatchRepository {

    private final SpringDataTicketBatchRepository jpaRepo;

    @Override
    public Optional<TicketBatchEntity> findById(UUID id) {
        return jpaRepo.findById(id);
    }

    @Override
    public UUID save(TicketBatchEntity ticketBatchEntity) {
        var savedTicketBatch = jpaRepo.save(ticketBatchEntity);
        return savedTicketBatch.getId();
    }
}

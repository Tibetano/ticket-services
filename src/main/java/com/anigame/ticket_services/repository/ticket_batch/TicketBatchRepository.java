package com.anigame.ticket_services.repository.ticket_batch;

import com.anigame.ticket_services.domain.TicketBatchEntity;
import com.anigame.ticket_services.shared.exception.exceptions.NotAvailableBatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketBatchRepository {

    private final SpringDataTicketBatchRepository jpaRepo;

    public TicketBatchEntity findById(UUID id) {

        TicketBatchEntity currentBatch = jpaRepo.findById(id).orElseThrow(()->new RuntimeException("Ticket batch not found."));


        /*
        ANALISAR ONDE DEVE FICAR A LÓGICA DE VERIFICAÇÃO DE LOTE ATIVO EXECUTADA ABAIXO POIS ELA NÃO DEVE ESTAR AQUI DENTRO DO REPOSITORY.
        */

        if (currentBatch.getStartAt().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Batch is not started.");
        } else if (currentBatch.getEndAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Batch ended.");
        }

        return currentBatch;
    }

    public TicketBatchEntity findActiveBatch() {
        return jpaRepo.findActiveBatch().orElseThrow(() -> new NotAvailableBatchException("Corrent active ticket batch not found."));
    }

    public UUID save(TicketBatchEntity ticketBatchEntity) {
        var savedTicketBatch = jpaRepo.save(ticketBatchEntity);
        return savedTicketBatch.getId();
    }
}

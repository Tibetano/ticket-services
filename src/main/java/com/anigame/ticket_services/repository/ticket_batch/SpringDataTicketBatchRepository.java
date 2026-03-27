package com.anigame.ticket_services.repository.ticket_batch;

import com.anigame.ticket_services.domain.TicketBatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataTicketBatchRepository extends JpaRepository<TicketBatchEntity, UUID> {

    Optional<TicketBatchEntity> findById(UUID id); // normal

    @Query("""
    SELECT b FROM TicketBatchEntity b
    WHERE b.startAt <= CURRENT_TIMESTAMP
      AND b.endAt >= CURRENT_TIMESTAMP
    """)
    Optional<TicketBatchEntity> findActiveBatch();



    //@EntityGraph(attributePaths = "ticketBatchType")
    //Optional<TicketBatchEntity> findByIdWithTypes(UUID id); // quando precisa
    /*
    a anotação acima faz o método gerar o seguinte sql:
    select tb.*, tbt.*
    from ticket_services.ticket_batch tb
    left join ticket_services.ticket_batch_type tbt
        on tbt.ticket_batch_id = tb.id
    where tb.id = ?

    */

}

package com.anigame.ticket_services.infrastructure.persistence.ticket_batch_type;

import com.anigame.ticket_services.domain.model.old_model.database.TicketBatchType;
import com.anigame.ticket_services.domain.model.old_model.database.TicketBatchTypeIdPrice;
import com.anigame.ticket_services.domain.model.old_model.database.enumerate.TicketBatchTypeEnum;
import com.anigame.ticket_services.domain.persistence.TicketBatchTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketBatchTypeRepositoryImpl implements TicketBatchTypeRepository {

    @PersistenceContext
    EntityManager entityManager;

    private final SpringDataTicketBatchTypeRepository jpaRepo;

    @Override
    public void save(TicketBatchType ticketBatchType) {

    }

    @Override
    @Transactional
    public Optional<TicketBatchTypeIdPrice> reserveTickets(UUID ticketBatchId, TicketBatchTypeEnum ticketType, Integer quantity) {

        String sql = """
            UPDATE ticket_services.ticket_batch_type
            SET reserved_quantity = reserved_quantity + :quantity
            WHERE ticket_batch_id = :ticketBatchId
              AND ticket_type = CAST(:ticketType AS ticket_type_enum)
              AND (total_quantity - sold_quantity - reserved_quantity) >= :quantity
            RETURNING id, price;
        """;

        try {
            Object[] result = (Object[]) entityManager.createNativeQuery(sql)
                    .setParameter("quantity", quantity)
                    .setParameter("ticketBatchId", ticketBatchId)
                    .setParameter("ticketType", ticketType.name())
                    .getSingleResult();

            return Optional.of(
                    TicketBatchTypeIdPrice.builder()
                            .id((UUID) result[0])
                            .price((Integer) result[1])
                            .build()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}

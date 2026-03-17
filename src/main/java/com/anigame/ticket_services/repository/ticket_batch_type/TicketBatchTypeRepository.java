package com.anigame.ticket_services.repository.ticket_batch_type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketBatchTypeRepository {
/*
    @PersistenceContext
    EntityManager entityManager;

    private final SpringDataTicketBatchTypeRepository jpaRepo;

    public void save(TicketBatchTypeEntity ticketBatchTypeEntity) {

    }

    @Transactional
    public Optional<TicketBatchTypeIdPrice> reserveTickets(UUID ticketBatchId, TicketBatchTypeEnum ticketType, Integer quantity) {

        //abaixo tem se a definição da classe TicketBatchTypeIdPrice
        //public class TicketBatchTypeIdPrice {
        //    private UUID id;
        //    private Integer price;
        //}

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
    }*/
}

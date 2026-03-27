package com.anigame.ticket_services.repository.ticket_batch_type;

import com.anigame.ticket_services.domain.TicketBatchTypeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketBatchTypeRepository {

    private  final SpringDataTicketBatchTypeRepository jpaRepo;
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


/*
    public void releaseReservation (List<OrderItemEntity> items) {
        List<UUID> ticketBatchTypeIds = items.stream().map(OrderItemEntity::getId).toList();
        Map<UUID,Integer> ticketQuantity = items.stream().collect(Collectors.toMap(
                OrderItemEntity::getTicketBatchTypeId,
                OrderItemEntity::getQuantity
        ));

        List<TicketBatchTypeEntity> ticketBatchTypes = jpaRepo.findByIdIn(ticketBatchTypeIds);

        //percorrer os lotes e pegando os valores nos items
        for(var t : ticketBatchTypes) {
            t.releaseTickets(ticketQuantity.get(t.getId()));
        }

    }*/
    public List<TicketBatchTypeEntity> findByIdIn (Set<UUID> ids) {
        return jpaRepo.findByIdIn(ids.stream().toList());
    }

    public void updateStockByOrder(UUID orderId) {
        jpaRepo.updateStockByOrder(orderId);
    }

}

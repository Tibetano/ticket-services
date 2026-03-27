package com.anigame.ticket_services.repository.ticket_batch_type;

import com.anigame.ticket_services.domain.TicketBatchTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringDataTicketBatchTypeRepository extends JpaRepository<TicketBatchTypeEntity, UUID> {
    List<TicketBatchTypeEntity> findByIdIn (List<UUID> idList);

    @Modifying
    @Query(value = """
        UPDATE ticket_services.ticket_batch_type tbt
        SET 
            sold_quantity = sold_quantity + oi.quantity,
            reserved_quantity = reserved_quantity - oi.quantity
        FROM ticket_services.order_item oi
        WHERE oi.ticket_batch_type_id = tbt.id
        AND oi.order_id = :orderId
        """, nativeQuery = true)
    void updateStockByOrder(UUID orderId);
}

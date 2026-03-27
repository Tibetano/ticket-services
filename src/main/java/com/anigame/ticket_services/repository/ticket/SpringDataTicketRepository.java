package com.anigame.ticket_services.repository.ticket;

import com.anigame.ticket_services.domain.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SpringDataTicketRepository extends JpaRepository<TicketEntity, UUID> {

    @Modifying
    @Query(value = """
        INSERT INTO ticket_services.ticket (
            order_item_id,
            ticket_batch_type_id,
            qr_code_hash
        )
        SELECT
            oi.id,
            oi.ticket_batch_type_id,
            gen_random_uuid()::text
        FROM ticket_services.order_item oi
        JOIN generate_series(1, oi.quantity) gs ON true
        WHERE oi.order_id = :orderId
        """, nativeQuery = true)
    void generateTicketsByOrder(UUID orderId);

    // 🔒 idempotência
    @Query(value = """
        SELECT COUNT(1) > 0
        FROM ticket_services.ticket t
        JOIN ticket_services.order_item oi ON oi.id = t.order_item_id
        WHERE oi.order_id = :orderId
        """, nativeQuery = true)
    boolean existsByOrderId(UUID orderId);

}

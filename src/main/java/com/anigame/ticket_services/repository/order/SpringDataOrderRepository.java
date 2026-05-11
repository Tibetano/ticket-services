package com.anigame.ticket_services.repository.order;

import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.domain.order.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, UUID> {
    //código abaixo tem objetivo de fazer o look e busca de três orders por vez evitando que outra app tente processar os mesmos pedidos
    @Query(
            value = """
    SELECT *
    FROM ticket_services.orders
    WHERE status = CAST(:status AS ticket_services.order_status)
    AND expires_at < now()
    LIMIT :limit
    FOR UPDATE SKIP LOCKED
    """,
            nativeQuery = true
    )
    /*@Query(
            value = """
    SELECT *
    FROM ticket_services.orders
    WHERE status = CAST(:status AS order_status)
    AND expires_at < now()
    LIMIT :limit
    FOR UPDATE SKIP LOCKED
    """,
            nativeQuery = true
    )*/
    List<OrderEntity> findExpiredPendingOrdersForUpdate(
            @Param("status") String status,
            @Param("limit") int limit
    );

    List<OrderEntity> findByCustomerIdAndStatusIn(
            UUID customerId,
            List<OrderStatusEntity> statuses
    );
}

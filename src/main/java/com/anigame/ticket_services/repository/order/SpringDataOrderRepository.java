package com.anigame.ticket_services.repository.order;

import com.anigame.ticket_services.domain.OrderEntity;
import com.anigame.ticket_services.domain.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByStatusAndExpiresAtBefore(OrderStatusEntity status, LocalDateTime now);
}

package com.anigame.ticket_services.repository.order_item;

import com.anigame.ticket_services.domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataOrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
}

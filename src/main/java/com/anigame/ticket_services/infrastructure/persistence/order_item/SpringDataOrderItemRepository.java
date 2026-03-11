package com.anigame.ticket_services.infrastructure.persistence.order_item;

import com.anigame.ticket_services.z_domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataOrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
}

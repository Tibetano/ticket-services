package com.anigame.ticket_services.infrastructure.persistence.order;

import com.anigame.ticket_services.z_domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, UUID> {
}

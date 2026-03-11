package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.z_domain.OrderEntity;

public interface OrderRepository {
    OrderEntity save(OrderEntity order);
}

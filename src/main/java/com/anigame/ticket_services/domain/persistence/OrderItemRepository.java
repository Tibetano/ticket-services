package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.model.database.OrderItem;

public interface OrderItemRepository {
    void save(OrderItem orderItem);
}

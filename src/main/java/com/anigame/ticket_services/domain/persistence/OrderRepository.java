package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.model.database.Order;

public interface OrderRepository {
    void save(Order order);
}

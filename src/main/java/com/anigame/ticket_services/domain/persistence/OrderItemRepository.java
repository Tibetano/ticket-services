package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.z_domain.OrderItemEntity;
import com.anigame.ticket_services.domain.model.old_model.database.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    OrderItem save(OrderItem orderItem);
    void saveAll (List<OrderItemEntity> orderItemList);
}

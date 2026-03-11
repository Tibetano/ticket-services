package com.anigame.ticket_services.data.mapper;

import com.anigame.ticket_services.domain.model.old_model.database.OrderItem;
import com.anigame.ticket_services.z_domain.OrderItemEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemEntity toEntityWithoutId (OrderItem orderItem) {
        return OrderItemEntity.builder()
                //.orderId(orderItem.getOrderId())
                .ticketBatchTypeId(orderItem.getTicketBatchTypeId())
                .unitPrice(orderItem.getUnitPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public OrderItem toDomainWithId (OrderItemEntity orderItemEntity) {
        return OrderItem.builder()
                .id(orderItemEntity.getId())
                //.orderId(orderItemEntity.getOrderId())
                .ticketBatchTypeId(orderItemEntity.getTicketBatchTypeId())
                .unitPrice(orderItemEntity.getUnitPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }
}

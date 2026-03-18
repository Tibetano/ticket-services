package com.anigame.ticket_services.repository.order_item;

import com.anigame.ticket_services.domain.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final SpringDataOrderItemRepository jpaRepo;

    public OrderItemEntity save(OrderItemEntity orderItemEntity) {
        return jpaRepo.save(orderItemEntity);
    }

    public void saveAll(List<OrderItemEntity> orderItemList) {
        jpaRepo.saveAll(orderItemList);
    }
}

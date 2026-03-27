package com.anigame.ticket_services.repository.order;

import com.anigame.ticket_services.domain.OrderEntity;
import com.anigame.ticket_services.domain.enums.OrderStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final SpringDataOrderRepository jpaRepo;

    public List<OrderEntity> findExpiredPendingOrders(int limit) {
        return jpaRepo.findExpiredPendingOrdersForUpdate(
                OrderStatusEntity.PENDING.name(),
                limit
        );
    }

    public OrderEntity save(OrderEntity order) {
        return jpaRepo.save(order);
    }
}

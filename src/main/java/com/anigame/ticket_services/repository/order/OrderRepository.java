package com.anigame.ticket_services.repository.order;

import com.anigame.ticket_services.domain.OrderEntity;
import com.anigame.ticket_services.domain.OrderStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final SpringDataOrderRepository jpaRepo;

    public List<OrderEntity> findExpiredPendingOrders (){
        return jpaRepo.findByStatusAndExpiresAtBefore(OrderStatusEntity.PENDING, LocalDateTime.now());
    };

    public OrderEntity save(OrderEntity order) {
        return jpaRepo.save(order);
    }
}

package com.anigame.ticket_services.repository.order;

import com.anigame.ticket_services.domain.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRepository {

    private final SpringDataOrderRepository jpaRepo;

    public OrderEntity save(OrderEntity order) {
        return jpaRepo.save(order);
    }
}

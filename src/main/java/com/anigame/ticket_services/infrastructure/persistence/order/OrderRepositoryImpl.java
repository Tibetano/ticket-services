package com.anigame.ticket_services.infrastructure.persistence.order;

import com.anigame.ticket_services.z_domain.OrderEntity;
import com.anigame.ticket_services.domain.persistence.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final SpringDataOrderRepository jpaRepo;
    //private final OrderMapper orderMapper;

    @Override
    public OrderEntity save(OrderEntity order) {

        /*var dbOrder = jpaRepo.save(
                orderMapper.toEntityWithoutId(order)
        );

        return orderMapper.toDomainWithId(dbOrder);*/

        return jpaRepo.save(order);
    }
}

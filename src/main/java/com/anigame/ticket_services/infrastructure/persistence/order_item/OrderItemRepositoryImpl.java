package com.anigame.ticket_services.infrastructure.persistence.order_item;

import com.anigame.ticket_services.data.mapper.OrderItemMapper;
import com.anigame.ticket_services.z_domain.OrderItemEntity;
import com.anigame.ticket_services.domain.model.old_model.database.OrderItem;
import com.anigame.ticket_services.domain.persistence.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final SpringDataOrderItemRepository jpaRepo;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItem save(OrderItem orderItem) {

        var dbOrderItem = jpaRepo.save(
                orderItemMapper.toEntityWithoutId(orderItem)
        );

        return orderItemMapper.toDomainWithId(dbOrderItem);
    }

    @Override
    public void saveAll(List<OrderItemEntity> orderItemList) {
        jpaRepo.saveAll(orderItemList);
    }
}

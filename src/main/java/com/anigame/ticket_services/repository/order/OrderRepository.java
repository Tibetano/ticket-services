package com.anigame.ticket_services.repository.order;

import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.domain.order.OrderStatusEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;
    private final SpringDataOrderRepository jpaRepo;

    public List<OrderEntity> findExpiredPendingOrders(int limit) {
        return jpaRepo.findExpiredPendingOrdersForUpdate(
                OrderStatusEntity.PENDING.name(),
                limit
        );
    }

    public List<OrderEntity> findByCustomerId(UUID customerId) {
        return jpaRepo.findByCustomerIdAndStatusIn(
                customerId,
                List.of(OrderStatusEntity.PENDING, OrderStatusEntity.PAID)
        );
    }

    public OrderEntity save(OrderEntity order) {
        System.out.println(order.getItems());
        //OrderEntity newOrder = jpaRepo.save(order);
        OrderEntity newOrder = jpaRepo.saveAndFlush(order);
        entityManager.refresh(newOrder);// a chamada do refresh foi adicionada pois o objeto retornado pelo banco dpois do save não está retornando o valor "oerderNUmber" que é gerado automaicamente pelo banco, isso não acontece em versões mais atuais do postgres, entretanto a que está sendo utilizada no momento não faz isso automaticamente. verifique se a versão do postgres é mais atual e se for remova esse entitymanager....
        return newOrder;
    }
}

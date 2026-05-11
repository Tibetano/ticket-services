package com.anigame.ticket_services.usecase.order;

import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.repository.order.OrderRepository;
import com.anigame.ticket_services.repository.ticket_batch_type.TicketBatchTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpireOrdersUseCase {

    private final OrderRepository orderRepository;
    private final TicketBatchTypeRepository stockRepository;

    @Transactional
    public void execute() {

        var expiredOrders = orderRepository.findExpiredPendingOrders(100);
        processOrders(expiredOrders);
    }

    private void processOrders(List<OrderEntity> orders) {

        Map<UUID, Integer> ticketQuantityList = new HashMap<>();
        Set<UUID> ticketBatchTypeIds = new HashSet<>();

        for (var order : orders) {
            for (var item : order.getItems()) {
                ticketQuantityList.merge(
                        item.getTicketBatchTypeEntity().getId(),
                        item.getQuantity(),
                        Integer::sum
                );
                ticketBatchTypeIds.add(item.getTicketBatchTypeEntity().getId());
            }
            order.expire();
        }

        var ticketBatchTypes = stockRepository.findByIdIn(ticketBatchTypeIds);

        for (var t : ticketBatchTypes) {
            t.releaseReservedTickets(ticketQuantityList.get(t.getId()));
        }
    }


}

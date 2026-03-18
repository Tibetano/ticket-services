package com.anigame.ticket_services.usecase;

import com.anigame.ticket_services.domain.TicketBatchTypeEntity;
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
        var expiredOrders = orderRepository.findExpiredPendingOrders();

        Set<UUID> ticketBatchTypeIds = new HashSet<>();

        //mapa que vai conter o par (ticketbatchtypeId, quantidade total a ser liberada nesse lote)
        Map<UUID, Integer> ticketQuantityList = new HashMap<>();

        for (var order : expiredOrders) {
            for (var item : order.getItems()) {
                if (!ticketQuantityList.containsKey(item.getTicketBatchTypeId())){
                    ticketQuantityList.put(item.getTicketBatchTypeId(), item.getQuantity());
                    ticketBatchTypeIds.add(item.getTicketBatchTypeId());
                } else {
                    ticketQuantityList.compute(item.getTicketBatchTypeId(), (k, v) -> v + item.getQuantity());
                }
            }
            //order.expire();
            order.expire();
        }

        List<TicketBatchTypeEntity> ticketBatchTypes = stockRepository.findByIdIn(ticketBatchTypeIds);

        for(var t : ticketBatchTypes) {
                t.releaseTickets(ticketQuantityList.get(t.getId()));
        }

    }

}

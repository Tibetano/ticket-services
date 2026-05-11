package com.anigame.ticket_services.usecase;

import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.domain.order.OrderStatusEntity;
import com.anigame.ticket_services.repository.ticket.TicketRepository;
import com.anigame.ticket_services.repository.ticket_batch_type.TicketBatchTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTicketUseCase {

    private final TicketRepository ticketRepository;
    private final TicketBatchTypeRepository ticketBatchTypeRepository;

    @Transactional
    public void execute (OrderEntity order) {

        // 🔒 regra de negócio
        if (order.getStatus() != OrderStatusEntity.PAID) {
            throw new RuntimeException("Order must be paid");
        }

        /*// 🔒 idempotência (ESSENCIAL)
        if (ticketRepository.existsByOrderId(orderId)) {
            return;
        }*/

        // 🚀 1. Gera TODOS os tickets (sem loop)
        ticketRepository.generateTicketsByOrder(order.getId());

       /* // 🚀 2. Atualiza estoque (sem loop) VERIFICAR SE ESSE MÉTODO ESTÁ DANDO BAIXA NO BANCO, OU SEJA, SE ELE ESTÁ REDUZINDO A QUANTIDADE DE INGRESSOS NOS INGRESSOS RESERVADOS E INCREMENTANDO NOS INGRESSOS VENDIDOS
        ticketBatchTypeRepository.updateStockByOrder(order.getId());
*/ //essa lógica foi removida pois não deveria ficar aqui
    }
}

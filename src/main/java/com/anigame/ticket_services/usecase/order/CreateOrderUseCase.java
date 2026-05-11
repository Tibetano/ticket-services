package com.anigame.ticket_services.usecase.order;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.OrderItemEntity;
import com.anigame.ticket_services.domain.TicketBatchEntity;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.domain.order.OrderVerify;
import com.anigame.ticket_services.infrastructure.clients.UserAccountService;
import com.anigame.ticket_services.repository.order.OrderRepository;
import com.anigame.ticket_services.repository.ticket_batch.TicketBatchRepository;
import com.anigame.ticket_services.usecase.payment.PaymentProcessor;
import com.anigame.ticket_services.web.dto.request.OrderRequestDTO;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final UserAccountService userAccountService;
    private final PaymentProcessor paymentProcessor;
    private final OrderRepository orderRepository;
    private final TicketBatchRepository ticketBatchRepository;
    private final OrderVerify orderVerify;

    @Transactional
    public OrderResponseDTO execute(String authorizationToken, OrderRequestDTO dto) {

        //obter os dados id, nome e cpf para a criação do pedido e cobrança.
        Customer customer = userAccountService.getVerifiedUser(authorizationToken);

        orderVerify.verifyTicketDisponibility(customer.getId(), dto);

        // valida lote
        // busca o lote que deverá puxar os tipos de ingresso(modelar o relacionamento nas entidades). Se não encontrar retorna Lote inválido.
        TicketBatchEntity ticketBatch = ticketBatchRepository.findById(dto.batch().id());

        // reserva ingressos
        ticketBatch.reserveTickets(dto.tickets());

        // criar lista de intens pedido
        List<OrderItemEntity> orderItemList = OrderItemEntity.createOrderItemList(
                ticketBatch.getTicketBatchType(),
                dto.tickets()
        );

        // calcula valor real (NUNCA usa valor do client)
        // cria o pedido
        OrderEntity order = OrderEntity.createWithOrderItemList(
                customer.getId(),
                10L,
                orderItemList
        );

        // salva o pedido para pegar o numero do pedido.
        var savedOrder = orderRepository.save(order);

        return paymentProcessor.process(customer, savedOrder, dto.payment());
    }
}

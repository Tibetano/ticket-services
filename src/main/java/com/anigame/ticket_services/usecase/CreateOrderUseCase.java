package com.anigame.ticket_services.usecase;

import com.anigame.ticket_services.data.dto.OrderRequestDTO;
import com.anigame.ticket_services.data.dto.OrderResponseDTO;
import com.anigame.ticket_services.z_domain.OrderEntity;
import com.anigame.ticket_services.z_domain.OrderItemEntity;
import com.anigame.ticket_services.z_domain.TicketBatchEntity;
import com.anigame.ticket_services.domain.new_impl.Customer;
import com.anigame.ticket_services.domain.new_impl.PaymentProcessor;
import com.anigame.ticket_services.domain.persistence.OrderRepository;
import com.anigame.ticket_services.domain.persistence.TicketBatchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final PaymentProcessor paymentProcessor;
    private final OrderRepository orderRepository;
    private final TicketBatchRepository ticketBatchRepository;

    @Transactional
    public OrderResponseDTO execute(String authorizationHeader, OrderRequestDTO dto) {

        // valida lote
        // reserva ingressos
        // calcula valor real (NUNCA usa valor do client)

        //obter os dados id, nome e cpf para a criação do pedido e cobrança.
        //UserProfile userProfile = userAccountService.getVerifiedUser(authorizationToken);//esses nomes não estão semânticos
        //Customer customer = userAccountService.getVerifiedUser(authorizationToken);//utilizar essa assinatura
        var customer = Customer.builder()
               .id(UUID.randomUUID())
                .fullName("Lucas Maciel")
                .cpf("10212365606")
                .phoneNumber("3891452712")
                .email("lucas@email.com")
                .build();


        //busca o lote que deverá puxar os tipos de ingresso(modelar o relacionamento nas entidades). Se não encontrar retorna Lote inválido.
        TicketBatchEntity ticketBatch = ticketBatchRepository.findById(dto.batch().id())
                .orElseThrow(() -> new RuntimeException("Ticket batch not found."));

        //aqui deve reservar os ingressos. implementar o método de reservar
        ticketBatch.reserveTickets(dto.tickets());

        //criar lista de intens pedido
        List<OrderItemEntity> orderItemList = OrderItemEntity.createOrderItemList(
                ticketBatch.getTicketBatchType(),
                dto.tickets()
        );

        //cria o pedido
        OrderEntity order = OrderEntity.createWithOrderItemList(
                customer.getId(),
                10L,
                orderItemList
        );

        //salva o pedido e pega o id.
        var savedOrder = orderRepository.save(order);

        return paymentProcessor.process(customer, savedOrder, dto.payment());

    }
}

package com.anigame.ticket_services.domain.order;

import com.anigame.ticket_services.domain.OrderItemEntity;
import com.anigame.ticket_services.repository.order.OrderRepository;
import com.anigame.ticket_services.shared.exception.exceptions.InsufficientTicketsException;
import com.anigame.ticket_services.web.dto.request.OrderRequestDTO;
import com.anigame.ticket_services.web.dto.request.TicketRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderVerify {

    private final OrderRepository orderRepository;
    @Value("${event.ticket-limit-per-user}")
    private Long ticketLimitPerUser;

    public void verifyTicketDisponibility (UUID customerId, OrderRequestDTO dto) {

        var totalNumberOfTickets = dto.tickets().stream().mapToInt(TicketRequestDTO::quantity).sum();

        if (totalNumberOfTickets == 0) {
            throw new RuntimeException("Orders must include at least one ticket.");
        }

        if (totalNumberOfTickets > ticketLimitPerUser) {
            throw new RuntimeException("The limit of tickets per user is " + ticketLimitPerUser + ".");
        }

        var dbOrders = orderRepository.findByCustomerId(customerId);

        var dbPaidNumberOfTickets = dbOrders.stream().filter(order->order.getStatus().equals(OrderStatusEntity.PAID))
                .mapToInt(order -> order.getItems().stream().mapToInt(OrderItemEntity::getQuantity).sum()).sum();

        var dbWaitingNumberOfTickets = dbOrders.stream().filter(order->order.getStatus().equals(OrderStatusEntity.PENDING))
                .mapToInt(order -> order.getItems().stream().mapToInt(OrderItemEntity::getQuantity).sum()).sum();

        var dbAvailableNumberOfTickets = 3 - (dbPaidNumberOfTickets + dbWaitingNumberOfTickets);

        if (dbAvailableNumberOfTickets - totalNumberOfTickets < 0) {
            throw new InsufficientTicketsException(
                    "Ticket limit per user exceeded.",
                    Map.of(
                            "ticket_limit_per_user",ticketLimitPerUser,
                            "tickets_requested",totalNumberOfTickets,
                            "tickets_available",dbAvailableNumberOfTickets,
                            "tickets_paid",dbPaidNumberOfTickets,
                            "tickets_pending",dbWaitingNumberOfTickets
                    )
            );
        }

    }
}


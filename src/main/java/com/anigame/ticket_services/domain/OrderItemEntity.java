package com.anigame.ticket_services.domain;

import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.web.dto.request.TicketRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "order_item",
        schema = "ticket_services"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    //@Column(name = "order_id", nullable = false)
    //private UUID orderId;
    //@Column(name = "ticket_batch_type_id", nullable = false)
    //private UUID ticketBatchTypeId;
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    private OrderEntity orderEntity;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_batch_type_id", nullable = false)
    @ToString.Exclude
    private TicketBatchTypeEntity ticketBatchTypeEntity;


    public static List<OrderItemEntity>  createOrderItemList (
            List<TicketBatchTypeEntity> ticketBatchTypeList,
            List<TicketRequestDTO> tickets
    ) {

        Map<String, TicketBatchTypeEntity> ticketTypeInfo = ticketBatchTypeList.stream()
                .collect(Collectors.toMap(
                        e -> e.getTicketType().name(),
                        e -> e
                ));

        return tickets.stream()
                .filter(t->t.quantity()>0).
                map(t->{
                    return OrderItemEntity.builder()
                        .ticketBatchTypeEntity(ticketTypeInfo.get(t.type()))
                        .unitPrice(ticketTypeInfo.get(t.type()).getPrice())
                        .quantity(t.quantity())
                        .build();}).toList();
    }

    public void confirmReservedTickets () {
        this.ticketBatchTypeEntity.confirmReservedTickets(this.quantity);
    }
}

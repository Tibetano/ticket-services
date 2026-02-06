package com.anigame.ticket_services.infrastructure.persistence.order_item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        name = "order_item",
        schema = "ticket_services"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    @Column(name = "ticket_batch_type_id", nullable = false)
    private UUID ticketBatchTypeId;
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private Integer quantity;
}

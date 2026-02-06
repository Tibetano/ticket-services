package com.anigame.ticket_services.infrastructure.persistence.ticket_batch_type;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        name = "ticket_batch_type",
        schema = "ticket_services"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketBatchTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "ticket_batch_id", nullable = false)
    private UUID ticketBatchId;
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type", nullable = false)
    private TicketBatchTypeEnumEntity ticketType;
    @Column(nullable = false)
    private Integer price;
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;
    @Column(name = "sold_quantity", nullable = false)
    private Integer soldQuantity = 0;
    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity = 0;
}

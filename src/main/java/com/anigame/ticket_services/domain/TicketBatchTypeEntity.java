package com.anigame.ticket_services.domain;

import com.anigame.ticket_services.domain.enums.TicketBatchTypeEnumEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "ticket_batch_type",
        schema = "ticket_services"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketBatchTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    //@Column(name = "ticket_batch_id", nullable = false)
    //private UUID ticketBatchId;

    //@Enumerated(EnumType.STRING)
    //@Column(name = "ticket_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "ticket_type", columnDefinition = "ticket_type_enum", nullable = false)
    private TicketBatchTypeEnumEntity ticketType;
    @Column(nullable = false)
    private Integer price;
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;
    @Column(name = "sold_quantity", nullable = false)
    private Integer soldQuantity = 0;
    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity = 0;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_batch_id", nullable = false)
    @ToString.Exclude
    private TicketBatchEntity ticketBatchEntity;

    @OneToMany(
            mappedBy = "ticketBatchTypeEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<OrderItemEntity> orderItemEntity;



    public void reserveTickets(Integer ticketQuantity) {
        if (soldQuantity + reservedQuantity + ticketQuantity > totalQuantity) {
            throw new RuntimeException("The number of tickets listed for reservation is greater than the number of tickets available!");
        }
        reservedQuantity+=ticketQuantity;
    }

    public void releaseReservedTickets(Integer ticketQuantity) {
        if (reservedQuantity < ticketQuantity) {
            throw new RuntimeException("The number of tickets listed for release is greater than the number of tickets available!");
        }
        reservedQuantity -= ticketQuantity;
    }

    public void confirmReservedTickets(Integer ticketQuantity) {
        if (reservedQuantity < ticketQuantity) {
            throw new RuntimeException("The number of tickets requested for confirmation is greater than the number of tickets available!");
        }
        reservedQuantity -= ticketQuantity;
        soldQuantity += ticketQuantity;
    }

}

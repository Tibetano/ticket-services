package com.anigame.ticket_services.domain;

import com.anigame.ticket_services.web.dto.request.TicketRequestDTO;
import com.anigame.ticket_services.domain.enums.TicketBatchStatusEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "ticket_batch",
        schema = "ticket_services"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketBatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "event_id", nullable = false)
    private UUID eventId;
    @Column(nullable = false, length = 120)
    private String name;
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketBatchStatusEntity status = TicketBatchStatusEntity.HIDDEN;

    @OneToMany(
            mappedBy = "ticketBatchEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TicketBatchTypeEntity> ticketBatchType;

    public void reserveTickets (List<TicketRequestDTO> tickets) {


        tickets.forEach(ticket -> {//avaliar mudar os tipos dos atributos para um map para eveitar esses for's aninhados
            ticketBatchType.forEach(tEntity -> {
                if (ticket.type().equals(tEntity.getTicketType().name())) {
                    tEntity.reserveTickets(ticket.quantity());
                }
            });
        });

    }

}

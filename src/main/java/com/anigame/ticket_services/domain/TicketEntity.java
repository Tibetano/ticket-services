package com.anigame.ticket_services.domain;

import com.anigame.ticket_services.domain.enums.TicketStatusEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "ticket",
        schema = "ticket_services"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_item_id", nullable = false)
    private UUID orderItemId;
    @Column(name = "ticket_batch_type_id", nullable = false)
    private UUID ticketBatchTypeId;

    @Column(name = "owner_name", length = 150)
    private String ownerName;
    @Column(name = "qr_code_hash", nullable = false, unique = true)
    private String qrCodeHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatusEntity status = TicketStatusEntity.VALID;
    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;
    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;

}

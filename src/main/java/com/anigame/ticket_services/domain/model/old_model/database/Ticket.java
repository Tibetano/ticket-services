package com.anigame.ticket_services.domain.model.old_model.database;

import com.anigame.ticket_services.domain.model.old_model.database.enumerate.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Ticket {
    private UUID id;
    private UUID orderItemId;
    private UUID ticketBatchTypeId;
    private String ownerName;
    private String qrCodeHash;
    private TicketStatus status;
    private LocalDateTime issuedAt;
    private LocalDateTime checkedInAt;

}

package com.anigame.ticket_services.domain.model.database;

import com.anigame.ticket_services.domain.model.database.enumerate.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

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

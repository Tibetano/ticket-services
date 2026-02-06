package com.anigame.ticket_services.domain.model.database;

import com.anigame.ticket_services.domain.model.database.enumerate.TicketBatchStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketBatch {
    private UUID id;
    private UUID eventId;
    private String name;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private TicketBatchStatus status;
}

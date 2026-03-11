package com.anigame.ticket_services.domain.model.old_model.database;

import com.anigame.ticket_services.domain.model.old_model.database.enumerate.TicketBatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class TicketBatch {
    private UUID id;
    private UUID eventId;
    private String name;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private TicketBatchStatus status;
}

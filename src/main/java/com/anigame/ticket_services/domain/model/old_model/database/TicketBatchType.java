package com.anigame.ticket_services.domain.model.old_model.database;

import com.anigame.ticket_services.domain.model.old_model.database.enumerate.TicketBatchTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class TicketBatchType {
    private UUID id;
    private UUID ticketBatchId;
    private TicketBatchTypeEnum ticketType;
    private Integer price;
    private Integer totalQuantity;
    private Integer soldQuantity;
    private Integer reservedQuantity;
}

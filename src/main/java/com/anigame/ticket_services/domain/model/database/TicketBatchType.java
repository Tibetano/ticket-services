package com.anigame.ticket_services.domain.model.database;

import com.anigame.ticket_services.domain.model.database.enumerate.TicketBatchTypeEnum;

import java.util.UUID;

public class TicketBatchType {
    private UUID id;
    private UUID ticketBatchId;
    private TicketBatchTypeEnum ticketType;
    private Integer price;
    private Integer totalQuantity;
    private Integer soldQuantity;
    private Integer reservedQuantity;
}

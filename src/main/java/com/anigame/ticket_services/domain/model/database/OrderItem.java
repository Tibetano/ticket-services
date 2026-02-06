package com.anigame.ticket_services.domain.model.database;

import java.util.UUID;

public class OrderItem {
    private UUID id;
    private UUID orderId;
    private UUID ticketBatchTypeId;
    private Integer unitPrice;
    private Integer quantity;
}

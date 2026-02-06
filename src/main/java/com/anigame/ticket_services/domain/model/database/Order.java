package com.anigame.ticket_services.domain.model.database;

import com.anigame.ticket_services.domain.model.database.enumerate.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private UUID id;
    private UUID customerId;
    private OrderStatus status;
    private Integer totalAmount;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}

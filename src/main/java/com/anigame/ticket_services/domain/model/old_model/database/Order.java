package com.anigame.ticket_services.domain.model.old_model.database;

import com.anigame.ticket_services.domain.model.old_model.database.enumerate.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Order {
    private UUID id;
    private UUID customerId;
    private OrderStatus status;
    private Integer totalAmount;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}

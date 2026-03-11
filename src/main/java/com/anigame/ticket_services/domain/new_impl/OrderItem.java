package com.anigame.ticket_services.domain.new_impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class OrderItem {
    private UUID id;
    private ItemType type;
    private Integer unitPrice;
    private Integer quantity;
}

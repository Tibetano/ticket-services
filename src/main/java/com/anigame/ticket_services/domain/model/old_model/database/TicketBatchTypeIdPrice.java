package com.anigame.ticket_services.domain.model.old_model.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class TicketBatchTypeIdPrice {
        private UUID id;
        private Integer price;
}

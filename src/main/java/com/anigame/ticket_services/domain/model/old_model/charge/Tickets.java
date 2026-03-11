package com.anigame.ticket_services.domain.model.old_model.charge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tickets {
    private UUID ticketBatchId;
    private List<Ticket> tickets;
}

package com.anigame.ticket_services.domain.model.old_model.charge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    private String type;
    private Integer quantity;
}

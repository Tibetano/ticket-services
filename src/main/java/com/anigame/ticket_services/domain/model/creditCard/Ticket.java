package com.anigame.ticket_services.domain.model.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    private String name;
    private Integer value;
    private Integer amount;
}

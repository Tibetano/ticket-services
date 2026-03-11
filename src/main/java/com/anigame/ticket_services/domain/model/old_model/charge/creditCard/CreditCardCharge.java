package com.anigame.ticket_services.domain.model.old_model.charge.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCardCharge {
    private List<Ticket22> tickets;
    private Payment payment;
}

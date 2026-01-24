package com.anigame.ticket_services.domain.model.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCardCharge {
    private List<Ticket> tickets;
    private Payment payment;
}

package com.anigame.ticket_services.domain.model.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCard {
    private Customer customer;
    private Integer installments;
    private String paymentToken;
}

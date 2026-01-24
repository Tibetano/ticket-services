package com.anigame.ticket_services.data.dto.creditCard;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditCardDTO(
        CustomerDTO customer,
        Integer installments,
        @JsonProperty("payment_token")
        String paymentToken
) {
}

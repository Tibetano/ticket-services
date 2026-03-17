package com.anigame.ticket_services.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditCardDTO(
        Integer installments,
        @JsonProperty("payment_token")
        String paymentToken
) {
}

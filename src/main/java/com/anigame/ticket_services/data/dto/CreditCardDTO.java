package com.anigame.ticket_services.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditCardDTO(
        Integer installments,
        @JsonProperty("payment_token")
        String paymentToken
) {
}

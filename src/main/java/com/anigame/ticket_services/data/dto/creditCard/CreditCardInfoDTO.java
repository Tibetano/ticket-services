package com.anigame.ticket_services.data.dto.creditCard;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditCardInfoDTO(
        CustomerInfoDTO customer,
        Integer installments,
        @JsonProperty("payment_token")
        String paymentToken
) {
}

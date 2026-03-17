package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditCard(
        Integer installments,

        @JsonProperty("payment_token")
        String paymentToken,

        Customer customer
) {}

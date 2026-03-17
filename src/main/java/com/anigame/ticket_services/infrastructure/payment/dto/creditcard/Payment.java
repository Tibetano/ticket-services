package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Payment(
        @JsonProperty("credit_card")
        CreditCard creditCard
) {}

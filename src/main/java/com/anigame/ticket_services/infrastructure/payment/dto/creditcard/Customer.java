package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Customer(
        String name,
        String cpf,

        @JsonProperty("phone_number")
        String phoneNumber,

        String email
) {}

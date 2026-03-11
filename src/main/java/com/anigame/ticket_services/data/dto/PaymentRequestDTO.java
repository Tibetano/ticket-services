package com.anigame.ticket_services.data.dto;

import com.anigame.ticket_services.domain.new_impl.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDTO(
        @NotNull
        PaymentMethod method,
        @Valid
        @JsonProperty("credit_card")
        CreditCardDTO creditCard
) {
}

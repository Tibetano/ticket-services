package com.anigame.ticket_services.web.dto.request;

import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.web.dto.CreditCardDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDTO(
        @NotNull
        PaymentMethodEnumEntity method,
        @Valid
        @JsonProperty("credit_card")
        CreditCardDTO creditCard
) {
}

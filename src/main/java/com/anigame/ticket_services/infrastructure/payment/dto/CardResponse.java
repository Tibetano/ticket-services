package com.anigame.ticket_services.infrastructure.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CardResponse(
        int code,
        Data data
) {
    public record Data(
            int installments,
            @JsonProperty("installment_value")
            int installmentValue,
            @JsonProperty("charge_id")
            Long chargeId,
            String status,
            int total,
            String payment
    ) {
    }
}

package com.anigame.ticket_services.usecase.webhook.dto.charge;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataDTO (
        String reason,
        IdentifiersDTO identifiers,
        @JsonProperty("custom_id")
        String customId,
        @JsonProperty("created_at")
        String createdAt,
        Integer id,
        String type,
        StatusDTO status,
        @JsonProperty("received_by_bank_at")
        String receivedByBankAt,
        Integer value
) {

        public record StatusDTO (
                String current,
                String previous
        ) {}

        public record IdentifiersDTO (
                @JsonProperty("charge_id")
                Long chargeId
        ) {}

}

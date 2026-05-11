package com.anigame.ticket_services.usecase.webhook.dto.charge;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IdentifiersDTO (
        @JsonProperty("charge_id")
        Long chargeId
) {

}

package com.anigame.ticket_services.usecase.webhook.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GnExtrasDTO(
        @JsonProperty("tarifa")
        String fee,

        @JsonProperty("pagador")
        PayerDTO payer
) {
}

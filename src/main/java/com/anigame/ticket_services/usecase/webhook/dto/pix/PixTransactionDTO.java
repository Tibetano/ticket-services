package com.anigame.ticket_services.usecase.webhook.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record PixTransactionDTO(
        @JsonProperty("endToEndId")
        String endToEndId,

        @JsonProperty("txid")
        String transactionId,

        @JsonProperty("chave")
        String key,

        @JsonProperty("valor")
        String amount,

        @JsonProperty("horario")
        Instant timestamp,

        @JsonProperty("infoPagador")
        String payerInfo,

        @JsonProperty("gnExtras")
        GnExtrasDTO extras
) {
}

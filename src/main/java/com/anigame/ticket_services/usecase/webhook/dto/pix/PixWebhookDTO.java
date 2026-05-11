package com.anigame.ticket_services.usecase.webhook.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PixWebhookDTO(
        @JsonProperty("pix")
        List<PixTransactionDTO> transactions
) {
}

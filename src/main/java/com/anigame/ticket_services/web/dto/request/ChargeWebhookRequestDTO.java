package com.anigame.ticket_services.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChargeWebhookRequestDTO(
        @JsonProperty("notification")
        String notificationToken
) {
}

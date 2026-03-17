package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Metadata(
        @JsonProperty("notification_url")
        String notificationUrl,

        @JsonProperty("custom_id")
        String customId
) {}

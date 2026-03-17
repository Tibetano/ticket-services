package com.anigame.ticket_services.infrastructure.payment.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Calendar(
        @JsonProperty("expiracao")
        Integer expiration
) {}

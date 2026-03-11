package com.anigame.ticket_services.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountStatusDTO(
        @JsonProperty("is_verified")
        boolean isVerified
) {
}

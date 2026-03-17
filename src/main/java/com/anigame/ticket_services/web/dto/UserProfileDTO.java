package com.anigame.ticket_services.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record UserProfileDTO(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String cpf,
        String gender,
        @JsonProperty("date_of_birth")
        String dateOfBirth,
        @JsonProperty("created_at")
        Instant createdAt
) {
}

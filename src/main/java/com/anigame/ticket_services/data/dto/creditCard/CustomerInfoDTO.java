package com.anigame.ticket_services.data.dto.creditCard;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerInfoDTO(
        String name,
        String cpf,
        String email,
        @JsonProperty("phone_number")
        String phoneNumber
) {
}

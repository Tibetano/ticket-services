package com.anigame.ticket_services.data.dto.pix;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerPixInfoReqDTO(
        String cpf,
        @JsonProperty("full_name")
        String fullName
) {
}

package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.TicketBatchTypeEnumEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserTicketResponseDTO(
        @JsonProperty("ticket_type")
        TicketBatchTypeEnumEntity ticketType,
        @JsonProperty("qr_code")
        String qrCode,
        @JsonProperty("event_edition")
        String eventEdition
) {
}

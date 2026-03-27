package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.TicketBatchTypeEnumEntity;
import com.anigame.ticket_services.domain.enums.TicketStatusEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserTicketResponseDTO(
        @JsonProperty("event_edition")
        String eventEdition,
        LocalDate date,
        @JsonProperty("ticket_type")
        TicketBatchTypeEnumEntity ticketType,
        TicketStatusEntity status,
        @JsonProperty("qr_code")
        String qrCode
) {
}

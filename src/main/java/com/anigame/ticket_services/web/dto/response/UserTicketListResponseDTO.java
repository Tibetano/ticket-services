package com.anigame.ticket_services.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record UserTicketListResponseDTO(
        @JsonProperty("ticket_list")
        List<UserTicketResponseDTO> ticketList
) {
}

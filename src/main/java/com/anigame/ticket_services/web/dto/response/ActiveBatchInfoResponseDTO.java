package com.anigame.ticket_services.web.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ActiveBatchInfoResponseDTO(
        BatchMetadataResponseDTO batch,
        List<BatchTicketResponseDTO> tickets
) {
}

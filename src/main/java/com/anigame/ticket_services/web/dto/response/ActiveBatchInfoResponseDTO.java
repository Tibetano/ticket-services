package com.anigame.ticket_services.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ActiveBatchInfoResponseDTO(
        BatchMetadataResponseDTO batch,
        List<BatchTicketResponseDTO> tickets,
        @JsonProperty("purchase_config")
        PurchaseConfigResponseDTO purchaseConfig
) {
}

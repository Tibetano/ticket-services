package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.TicketBatchTypeEnumEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record BatchTicketResponseDTO(
        TicketBatchTypeEnumEntity type,
        Integer price,
        @JsonProperty("total_quantity")
        Integer totalQuantity,
        //@JsonProperty("sold_quantity")
        //Integer soldQuantity,
        //@JsonProperty("reserved_quantity")
        //Integer reservedQuantity
        @JsonProperty("available_quantity")
        Integer availableQuantity
) {
}

package com.anigame.ticket_services.usecase.webhook.dto.charge;

import java.util.List;

public record EfiTransactionStatusResponseDTO(
        Integer code,
        List<DataDTO> data
) {

}

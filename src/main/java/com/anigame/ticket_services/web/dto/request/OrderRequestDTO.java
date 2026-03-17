package com.anigame.ticket_services.web.dto.request;


import com.anigame.ticket_services.web.dto.BatchDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(
        @NotNull
        BatchDTO batch,
        @NotNull
        List<TicketRequestDTO> tickets,

        @Valid
        @NotNull
        PaymentRequestDTO payment
) {
}

package com.anigame.ticket_services.data.dto;


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

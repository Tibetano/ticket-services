package com.anigame.ticket_services.data.dto.creditCard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreditCardPaymentDTO(
    List<TicketDTO> tickets,
    @JsonProperty("credit_card_info")
    CreditCardInfoDTO creditCardInfo
) {
}

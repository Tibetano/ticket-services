package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

public record Item(
        String name,
        Integer amount,
        Integer value
) {}

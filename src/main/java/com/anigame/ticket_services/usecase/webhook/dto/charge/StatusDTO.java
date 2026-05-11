package com.anigame.ticket_services.usecase.webhook.dto.charge;

public record StatusDTO (
        String current,
        String previous
) {
}

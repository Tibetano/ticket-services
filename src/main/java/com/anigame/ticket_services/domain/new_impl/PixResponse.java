package com.anigame.ticket_services.domain.new_impl;

import lombok.Builder;

@Builder
public record PixResponse(
        String txId,
        String locId
) {
}

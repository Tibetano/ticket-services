package com.anigame.ticket_services.web.dto.response;

import java.util.UUID;

public record BatchMetadataResponseDTO(
        UUID id,
        String name
) {
}

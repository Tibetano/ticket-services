package com.anigame.ticket_services.infrastructure.security.dto;

import java.time.Instant;
import java.util.Map;

public record JwtPayload(
        String id,
        String issuer,
        String subject,
        Instant issuedAt,
        Instant expiresAt,
        Map<String,String> claim
) {
}

package com.anigame.ticket_services.domain.service;

import java.time.Instant;
import java.util.Map;

public interface JwtService {
    String encode (String id, String issuer, String subject,
                   Instant issuedAt, Instant expiresAt, Map<String,String> claim
    );
    JwtPayload decode (String jwt);

    record JwtPayload (
            String id,
            String issuer,
            String subject,
            Instant issuedAt,
            Instant expiresAt,
            Map<String,String> claim
    ) {}
}

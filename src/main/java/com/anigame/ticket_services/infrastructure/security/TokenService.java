package com.anigame.ticket_services.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtCoDec jwtCoDec;

    public UUID getUserIdFromToken(String token) {
        var decodedJwt = jwtCoDec.decode(token);
        if (Instant.now().isAfter(decodedJwt.expiresAt())) {
            throw new RuntimeException("Token expired.");
        }
        return UUID.fromString(decodedJwt.subject());
    }

}

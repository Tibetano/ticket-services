package com.anigame.ticket_services.infrastructure.service.tokening;

import com.anigame.ticket_services.domain.service.JwtService;
import com.anigame.ticket_services.domain.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtService jwtService;

    @Override
    public UUID getUserIdFromToken(String token) {
        var decodedJwt = jwtService.decode(token);
        if (Instant.now().isAfter(decodedJwt.expiresAt())) {
            throw new RuntimeException("Token expired.");
        }
        return UUID.fromString(decodedJwt.subject());
    }

}

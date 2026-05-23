package com.anigame.ticket_services.infrastructure.security;

import com.anigame.ticket_services.infrastructure.clients.EfiPayAuthClient;
import com.anigame.ticket_services.infrastructure.clients.dto.TokenResponse;
import com.anigame.ticket_services.infrastructure.config.EfiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EfiCreditCardTokenService {

    private final EfiPayAuthClient authClient;
    private final EfiProperties properties;

    private String accessToken;
    private Instant expiresAt;

    public synchronized String getAccessToken() {

        if (tokenExpired()) {
            refreshToken();
        }

        return accessToken;
    }

    private boolean tokenExpired() {

        return accessToken == null
                || expiresAt == null
                || Instant.now().isAfter(expiresAt);
    }

    private void refreshToken() {

        String credentials =
                properties.getClientId()
                        + ":"
                        + properties.getClientSecret();

        String base64 =
                Base64.getEncoder()
                        .encodeToString(
                                credentials.getBytes(StandardCharsets.UTF_8)
                        );

        TokenResponse response = authClient.authorize(
                "Basic " + base64,
                Map.of(
                        "grant_type",
                        "client_credentials"
                )
        );

        this.accessToken = response.getAccessToken();

        this.expiresAt =
                Instant.now()
                        .plusSeconds(response.getExpiresIn() - 60);

    }
}

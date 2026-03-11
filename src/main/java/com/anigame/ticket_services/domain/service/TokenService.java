package com.anigame.ticket_services.domain.service;

import java.util.UUID;

public interface TokenService {
    UUID getUserIdFromToken (String token);
}

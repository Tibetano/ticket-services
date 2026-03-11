package com.anigame.ticket_services.domain.service;

import com.anigame.ticket_services.data.dto.UserProfileDTO;

public interface EventServicesService {
    UserProfileDTO getUserProfile(String authorizationToken);
}

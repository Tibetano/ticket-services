package com.anigame.ticket_services.domain.service;

import com.anigame.ticket_services.data.dto.AccountStatusDTO;

public interface AuthServicesService {
    AccountStatusDTO getAccountInfo(String authorizationToken);
}

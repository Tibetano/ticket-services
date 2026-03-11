package com.anigame.ticket_services.domain.service;

import com.anigame.ticket_services.domain.model.old_model.UserProfile;

public interface UserAccountService {
    UserProfile getVerifiedUser (String authorizationToken);
}

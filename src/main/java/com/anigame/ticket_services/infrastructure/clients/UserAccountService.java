package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.infrastructure.security.TokenService;
import com.anigame.ticket_services.web.dto.AccountStatusDTO;
import com.anigame.ticket_services.infrastructure.clients.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AuthServicesFeignClient authServicesFeignClient;
    private final EventServicesFeignClient eventServicesFeignClient;
    private final TokenService tokenService;

    public UserProfile getVerifiedUser(String authorizationToken) {

        AccountStatusDTO accountStatus = authServicesFeignClient.getAccountInfo(authorizationToken);

        if (!accountStatus.isVerified()) {
            throw new RuntimeException("ACCOUNT NOT VERIFIED!!!!!!!!!!!!!!");
        }

        //UserProfileDTO userProfile = eventServicesFeignClient.

        //var userId = tokenService.getUserIdFromToken(authorizationToken.replace("Bearer ",""));

        //AQUI VC GERA E RETORNA UM USERPROFILE COM OS DADOS OBTIDOS ACIMA

        return null;
    }

}

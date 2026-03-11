package com.anigame.ticket_services.infrastructure.service.user_account;

import com.anigame.ticket_services.data.dto.AccountStatusDTO;
import com.anigame.ticket_services.domain.model.old_model.UserProfile;
import com.anigame.ticket_services.domain.service.TokenService;
import com.anigame.ticket_services.domain.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final AuthServicesFeignClient authServicesFeignClient;
    private final EventServicesFeignClient eventServicesFeignClient;
    private final TokenService tokenService;

    @Override
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

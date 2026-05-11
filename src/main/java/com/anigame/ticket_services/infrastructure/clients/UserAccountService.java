package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.infrastructure.clients.dto.AccountInfoDTO;
import com.anigame.ticket_services.infrastructure.clients.dto.UserAccountStatus;
import com.anigame.ticket_services.infrastructure.clients.dto.UserProfileDTO;
import com.anigame.ticket_services.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AuthServicesFeignClient authServicesFeignClient;
    private final EventServicesFeignClient eventServicesFeignClient;
    private final TokenService tokenService;

    public Customer getVerifiedUser(String authorizationToken) {

        AccountInfoDTO accountInfo = authServicesFeignClient.getAccountInfo(authorizationToken);

        if (!accountInfo.status().equals(UserAccountStatus.VERIFIED)) {
            throw new RuntimeException("ACCOUNT NOT VERIFIED!");
        }

        UserProfileDTO userProfile = eventServicesFeignClient.getUserProfile(authorizationToken);

        var userId = tokenService.getUserIdFromToken(authorizationToken.replace("Bearer ",""));

        //AQUI VC GERA E RETORNA UM USERPROFILE COM OS DADOS OBTIDOS ACIMA
        return Customer.builder()
                .id(userId)
                .fullName(userProfile.fullName())
                .cpf(userProfile.cpf())
                .phoneNumber(userProfile.phoneNumber())
                .email(accountInfo.email())
                .build();

    }
}

package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.infrastructure.clients.dto.AccountInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("AuthenticationServicesClient")
public interface AuthServicesFeignClient {

    @GetMapping("/auth/info")
    AccountInfoDTO getAccountInfo (@RequestHeader("Authorization") String authorizationHeader);


    //IMPLEMENTAR AQUI TODAS AS REQUISIÇÕES DA SDK DO EFÍ QUE SERÃO UTILIZADAS AQUI


}

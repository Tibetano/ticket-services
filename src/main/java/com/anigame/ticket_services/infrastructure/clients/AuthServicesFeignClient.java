package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.web.dto.AccountStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("AuthenticationServicesClient")
public interface AuthServicesFeignClient {

    @GetMapping("/info")
    AccountStatusDTO getAccountInfo (@RequestHeader("Authorization") String authorizationHeader);

}

package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.infrastructure.clients.dto.UserProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("EventServicesClient")
public interface EventServicesFeignClient {

    @GetMapping("/v1/user/profile")
    UserProfileDTO getUserProfile (@RequestHeader("Authorization") String authorizationHeader);

}

package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.infrastructure.clients.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(
        contextId = "efiCardAuthClient",
        name = "efi-card-auth",
        url = "${efi.credit-card-base-url}"
)
public interface EfiPayAuthClient {

    @PostMapping(
            value = "v1/authorize",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    TokenResponse authorize(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, String> body
    );
}

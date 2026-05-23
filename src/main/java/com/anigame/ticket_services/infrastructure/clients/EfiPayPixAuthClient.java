package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.infrastructure.clients.dto.TokenResponse;
import com.anigame.ticket_services.infrastructure.config.EfiMtlsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(
        contextId = "efiAuthClient",
        name = "efi-auth",
        url = "${efi.pix-base-url}",
        configuration = EfiMtlsConfig.class
)
public interface EfiPayPixAuthClient {

    @PostMapping(
            value = "/oauth/token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    TokenResponse login(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, String> body
    );
}

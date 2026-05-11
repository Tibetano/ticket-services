package com.anigame.ticket_services.infrastructure.clients.dto;

public record AccountInfoDTO(
        //@JsonProperty("is_verified")
        //boolean isVerified
        String username,
        String email,
        UserAccountStatus status
) {
}

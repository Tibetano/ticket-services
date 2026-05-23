package com.anigame.ticket_services.infrastructure.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EfiPixQRCodeResponse(
        @JsonProperty("imagemQrcode")
        String qrCodeImage,
        @JsonProperty("qrcode")
        String qrCode,
        @JsonProperty("linkVisualizacao")
        String visualizationLink
) {
}

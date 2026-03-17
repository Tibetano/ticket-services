package com.anigame.ticket_services.infrastructure.payment.dto;

public record PixQRCodeResponse(
        String qrCodeImage,
        String qrCode,
        String visualizationLink
) {
}

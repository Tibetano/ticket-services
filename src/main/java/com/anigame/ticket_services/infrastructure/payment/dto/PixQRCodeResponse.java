package com.anigame.ticket_services.infrastructure.payment.dto;

import lombok.Builder;

@Builder
public record PixQRCodeResponse(
        String qrCodeImage,
        String qrCode,
        String visualizationLink
) {
}

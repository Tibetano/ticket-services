package com.anigame.ticket_services.domain.new_impl;

public record PixQRCodeResponse(
        String qrCodeImage,
        String qrCode,
        String visualizationLink
) {
}

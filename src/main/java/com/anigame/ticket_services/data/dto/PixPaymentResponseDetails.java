package com.anigame.ticket_services.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PixPaymentResponseDetails(
        @JsonProperty("qr_code_image")
        String qrCodeImage,
        @JsonProperty("qr_code")
        String qrCode,
        @JsonProperty("visualization_link")
        String visualizationLink
) {
}

package com.anigame.ticket_services.data.dto.pix.pixQrcode;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QrcodeResDTO(
        @JsonProperty("qr_code_image")
        String qrcodeImage,
        @JsonProperty("qr_code")
        String qrcode,
        @JsonProperty("visualization_link")
        String visualizationLink
) {
}
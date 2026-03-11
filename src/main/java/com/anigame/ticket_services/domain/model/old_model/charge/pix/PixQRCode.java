package com.anigame.ticket_services.domain.model.old_model.charge.pix;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PixQRCode {
    private String qrCodeImage;
    private String qrCode;
    private String visualizationLink;
}

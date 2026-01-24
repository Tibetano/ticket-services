package com.anigame.ticket_services.data.mapper;

import com.anigame.ticket_services.data.dto.pix.pixQrcode.QrcodeResDTO;
import com.anigame.ticket_services.domain.model.PixQRCode;
import org.springframework.stereotype.Component;

@Component
public class PixMapper {

    //PixQRCode -> QrcodeResDTO
    public static QrcodeResDTO toResDTO (PixQRCode pixQRCode) {
        return new QrcodeResDTO(
                pixQRCode.getQrCodeImage(),
                pixQRCode.getQrCode(),
                pixQRCode.getVisualizationLink()
        );
    }
}

package com.anigame.ticket_services.data.mapper;

import com.anigame.ticket_services.data.dto.pix.pixQrcode.QrcodeResDTO;
import com.anigame.ticket_services.domain.model.pix.PixQRCode;
import com.anigame.ticket_services.infrastructure.configuration.EfiProperties;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PixMapper {

    private final EfiProperties props;

    //PixQRCode -> QrcodeResDTO
    public QrcodeResDTO toResDTO (PixQRCode pixQRCode) {
        return new QrcodeResDTO(
                pixQRCode.getQrCodeImage(),
                pixQRCode.getQrCode(),
                pixQRCode.getVisualizationLink()
        );
    }

    public JSONObject toPixBody(String fullName, String cpf) {
        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", props.getQRCodeExpirationTime()));
        body.put("devedor", new JSONObject().put("cpf", cpf).put("nome", fullName));
        body.put("valor", new JSONObject().put("original", props.getOriginalTicketPrice()));
        body.put("chave", props.getPixKey());
        body.put("solicitacaoPagador", "Serviço realizado.");
        return body;
    }
}

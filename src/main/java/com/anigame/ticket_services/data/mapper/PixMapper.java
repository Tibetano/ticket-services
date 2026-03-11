package com.anigame.ticket_services.data.mapper;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class PixMapper {
/*
    //PixQRCode -> QrcodeResDTO
    public QrcodeResDTO toResDTO (PixQRCode pixQRCode) {
        return new QrcodeResDTO(
                pixQRCode.getQrCodeImage(),
                pixQRCode.getQrCode(),
                pixQRCode.getVisualizationLink()
        );
    }
*/
    public JSONObject toPixBody(String fullName, String cpf, Integer expirationTime, String originalTicketPrice, String pixKey) {
        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", expirationTime));
        body.put("devedor", new JSONObject().put("cpf", cpf).put("nome", fullName));
        body.put("valor", new JSONObject().put("original", originalTicketPrice));
        body.put("chave", pixKey);
        //body.put("solicitacaoPagador", "Serviço realizado.");
        return body;
    }
}

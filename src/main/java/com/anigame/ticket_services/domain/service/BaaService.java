package com.anigame.ticket_services.domain.service;

import com.anigame.ticket_services.domain.model.old_model.charge.pix.PixQRCode;
import com.anigame.ticket_services.domain.model.old_model.charge.creditCard.CreditCardCharge;

import java.net.URISyntaxException;
import java.util.UUID;

public interface BaaService {
    PixQRCode pixGenerateCharge(String fullName, String cpf, UUID orderId, Integer totalAmount) throws URISyntaxException;
    void generateCreditCardCharge(CreditCardCharge creditCardCharge);

}

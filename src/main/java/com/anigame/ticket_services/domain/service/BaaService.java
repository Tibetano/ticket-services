package com.anigame.ticket_services.domain.service;

import com.anigame.ticket_services.domain.model.pix.PixQRCode;
import com.anigame.ticket_services.domain.model.creditCard.CreditCardCharge;

import java.net.URISyntaxException;

public interface BaaService {
    PixQRCode generatePixCharge(String fullName, String cpf) throws URISyntaxException;
    void generateCreditCardCharge(CreditCardCharge creditCardCharge);

}

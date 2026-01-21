package com.anigame.ticket_services.domain.service;

import com.anigame.ticket_services.domain.model.PixQRCode;

import java.net.URISyntaxException;

public interface BaaService {
    PixQRCode generatePixCharge(String fullName, String cpf) throws URISyntaxException;
    PixQRCode generatePixChargeQRCcode(String fullName, String cpf);

}

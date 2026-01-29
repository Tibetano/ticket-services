package com.anigame.ticket_services.domain.usecase;

import com.anigame.ticket_services.domain.model.pix.PixQRCode;

import java.net.URISyntaxException;

public interface GenerateChargePixUseCase {
    PixQRCode execute (String fullName, String cpf) throws URISyntaxException;
}

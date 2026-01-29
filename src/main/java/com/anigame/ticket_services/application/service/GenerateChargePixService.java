package com.anigame.ticket_services.application.service;

import com.anigame.ticket_services.domain.model.pix.PixQRCode;
import com.anigame.ticket_services.domain.service.BaaService;
import com.anigame.ticket_services.domain.usecase.GenerateChargePixUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class GenerateChargePixService implements GenerateChargePixUseCase {

    private final BaaService baaService;

    @Override
    public PixQRCode execute(String fullName, String cpf) throws URISyntaxException {
        return baaService.generatePixCharge(fullName, cpf);
    }
}

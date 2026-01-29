package com.anigame.ticket_services.application.service;

import com.anigame.ticket_services.domain.model.creditCard.CreditCardCharge;
import com.anigame.ticket_services.domain.service.BaaService;
import com.anigame.ticket_services.domain.usecase.GenerateChargeCreditCardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateChargeCreditCardService implements GenerateChargeCreditCardUseCase {

    private final BaaService baaService;

    @Override
    public void execute(CreditCardCharge creditCardCharge) {
        baaService.generateCreditCardCharge(creditCardCharge);
    }
}

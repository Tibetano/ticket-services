package com.anigame.ticket_services.domain.usecase;

import com.anigame.ticket_services.domain.model.creditCard.CreditCardCharge;

public interface GenerateChargeCreditCardUseCase {
    public void execute (CreditCardCharge creditCardCharge);
}

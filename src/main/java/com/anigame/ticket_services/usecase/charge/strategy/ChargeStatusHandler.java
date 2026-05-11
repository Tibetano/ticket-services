package com.anigame.ticket_services.usecase.charge.strategy;

import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;

public interface ChargeStatusHandler {
    String supports ();
    void handle (DataDTO dto);
}

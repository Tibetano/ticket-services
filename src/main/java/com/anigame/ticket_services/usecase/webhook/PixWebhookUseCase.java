package com.anigame.ticket_services.usecase.webhook;

import com.anigame.ticket_services.usecase.pix.PixProcessor;
import com.anigame.ticket_services.usecase.webhook.dto.pix.PixWebhookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PixWebhookUseCase {

    private final PixProcessor pixProcessor;

    public void execute (PixWebhookDTO pixWebhookDTO) {
/*
        var lixo = new PixTransactionDTO(
                "qualquercoisa",
                "2584a3dd-90cc-4e31-b0d1-40611b8fb8a8",
                "askdfjhlkajs",
                "askjdfhsjakdhf",
                Instant.now(),
                "sdfjgkhdfg",
                null
        );

        pixWebhookDTO = new PixWebhookDTO(
                List.of(
                        lixo
                )
        );*/

        pixProcessor.process(pixWebhookDTO);

        //avaliar todos os tipos de estado de uma transação pix e tratar apenas quando o status pago estiver no webhook

    }
}

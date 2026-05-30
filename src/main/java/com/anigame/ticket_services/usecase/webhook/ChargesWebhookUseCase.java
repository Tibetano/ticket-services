package com.anigame.ticket_services.usecase.webhook;

import com.anigame.ticket_services.infrastructure.payment.PaymentGateway;
import com.anigame.ticket_services.usecase.charge.ChargeProcessor;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargesWebhookUseCase {

    private final ChargeProcessor chargeProcessor;
    private final PaymentGateway paymentGateway;

    public void execute (String notificationToken) {

        DataDTO latestStatus = paymentGateway.getPaymentStatus(notificationToken);

        System.out.println(
                "last status:" +
                "\n{" +
                "\nnotificationToken: " + notificationToken +
                "\ncorrentStatus: " +
                latestStatus.status().current() +
                "\npreviusStatus: " + latestStatus.status().previous() +
                "\n}"
        );

        /*var mockStatus = new DataDTO(
                "No-reason",
                new IdentifiersDTO(999142145L),
                "000_7",
                "2026-05-01",
                3,
                "charge",
                new StatusDTO("paid", "waiting"),
                "don't-relate",
                15000
        );*/

        //pegar o resultado do token e tomar a decisão de se criar um novo pedido e etc...
        chargeProcessor.process(latestStatus);
        //chargeProcessor.process(mockStatus);

    }

}

package com.anigame.ticket_services.web.webhook;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    @Transactional
    public void process(WebhookDTO dto) {

        /*Payment payment = paymentRepository.findByTransactionId(dto.id());

        if (dto.isPaid()) {
            payment.paid();
            payment.getOrder().paid();
        }*/
    }

}

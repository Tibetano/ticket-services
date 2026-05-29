package com.anigame.ticket_services.usecase.charge.strategy;

import com.anigame.ticket_services.repository.payment.PaymentRepository;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContestedHandler implements ChargeStatusHandler {

    private final PaymentRepository paymentRepository;

    @Override
    public String supports() {
        return "contested";
    }

    @Override
    public void handle(DataDTO dto) {

        var payment = paymentRepository.findByProviderChargeId(dto.identifiers().chargeId().toString());

        //verificar se a cobrança já foi tratada
        if (!payment.isContested()) {
            throw new RuntimeException("The charge already contested.");
        } else if (payment.isPending()) {
            throw new RuntimeException("Payment is pending.");
        }
        //atualizar status do registro na tabela payment
        payment.contest();

        //AQUI DEVE SER IMPLEMENTADO O ENVIO DE EMAIL PARA O ADM LUCAS AVISANDO QUE ESSE PAGAMENTO DE INGRESSO FOI CONTESTADO!
    }
}

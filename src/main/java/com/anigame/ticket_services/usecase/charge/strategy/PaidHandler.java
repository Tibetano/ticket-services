package com.anigame.ticket_services.usecase.charge.strategy;

import com.anigame.ticket_services.repository.payment.PaymentRepository;
import com.anigame.ticket_services.usecase.CreateTicketUseCase;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaidHandler implements ChargeStatusHandler {

    private final PaymentRepository paymentRepository;
    private final CreateTicketUseCase createTicketUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public String supports() {
        return "paid";
    }

    @Override
    @Transactional
    public void handle(DataDTO dto) {

        System.out.println("Entrou no tratador do paid.");

        var payment = paymentRepository.findByProviderChargeId(dto.identifiers().chargeId().toString());

        System.out.println("PaymentStatus: " + payment.getStatus());

        /*//verificar se a cobrança já foi tratada
        if (!payment.isConfirmed()) {
            throw new RuntimeException("The charge already processed or not approved yet.");
        }*/

        if (payment.isApproved()) {
            payment.confirm();
            try {
                payment.setRawResponse(objectMapper.writeValueAsString(dto));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Erro ao converter DTO para JSON", e);
            }
            createTicketUseCase.execute(payment.getOrderEntity());
            //atualizar status do registro na tabela payment
            payment.paid();
        } else if (payment.isConfirmed()) {
            //atualizar status do registro na tabela payment
            payment.paid();
        }



    }
}

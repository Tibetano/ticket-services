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

        var payment = paymentRepository.findByProviderChargeId(dto.identifiers().chargeId().toString());

        //verificar se a cobrança já foi tratada para evitar a geração de ingressos duplicados/mais ingressos, no caso, ingressos inválidos
        if (payment.isApproved()) {
            throw new RuntimeException("Charge already processed.");
        }
        //atualizar status do registro na tabela payment
        payment.confirm();

        /*
            O OBJETO COMPLETO DA NOTIFICAÇÃO DE PAGAMENTO DEVE SER SALVO COMO STRING NO ATRIBUTO "rawResponse" DA ENTIDADE DE PAYMENT!!!
            REPARE QUE ATUALMENTE O OBJETO DE NOTIFICAÇÃO NESSE PONTO DO FLUXO ESTÁ TRAZENDO APENAS UMA PARTE DA ENTIDADE ORIGINAL RETORNADA PELO EFÍ.
        */
        try {
            payment.setRawResponse(objectMapper.writeValueAsString(dto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter DTO para JSON", e);
        }

        //criar ingresso
        createTicketUseCase.execute(payment.getOrderEntity());

    }
}

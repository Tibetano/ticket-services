package com.anigame.ticket_services.usecase.pix;

import com.anigame.ticket_services.repository.payment.PaymentRepository;
import com.anigame.ticket_services.usecase.CreateTicketUseCase;
import com.anigame.ticket_services.usecase.webhook.dto.pix.PixWebhookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PixProcessor {

    private final PaymentRepository paymentRepository;
    private final CreateTicketUseCase createTicketUseCase;
    private final ObjectMapper objectMapper;

    @Transactional
    public void process (PixWebhookDTO dto) {

        if (dto.transactions().isEmpty()) {
            System.out.println("-------------Webhook id empty-------------");
            System.out.println(dto);
            System.out.println("------------------------------------------");
            return;
        }

        //buscar o payment referente ao pedido/pagamento
        var payment = paymentRepository.findByProviderTxId(dto.transactions().getFirst().transactionId());

        //verificar se a cobrança já foi tratada para evitar a geração de ingressos duplicados/mais ingressos, no caso, ingressos inválidos
        if (payment.isApproved()) {
            throw new RuntimeException("Charge already processed.");
        }
        //atualizar status do registro na tabela pedido
        payment.confirm();
        
        /*
            O OBJETO COMPLETO DA NOTIFICAÇÃO DE PAGAMENTO DEVE SER SALVO COMO STRING NO ATRIBUTO "rawResponse" DA ENTIDADE DE PAYMENT!!!
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

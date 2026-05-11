package com.anigame.ticket_services.usecase;

import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.repository.ticket_batch.TicketBatchRepository;
import com.anigame.ticket_services.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GetActiveBatchUseCase {

    private final TicketBatchRepository ticketBatchRepository;

    @Value("${event.ticket-limit-per-user}")
    private Long ticketLimitPerUser;

    @Value("${efi.credit-card-fee-type}")
    private String creditCardFeeType;
    @Value("${efi.credit-card-fee-value}")
    private BigDecimal creditCardFeeValue;
    @Value("${efi.pix-fee-type}")
    private String pixFeeType;
    @Value("${efi.pix-fee-value}")
    private BigDecimal pixFeeValue;

    public ActiveBatchInfoResponseDTO execute(){

        //a busca abaixo deverá retornar apenas um lote, com isso, vc deverá bolar uma estratégia para conseguir fazer isso (nesse caso atual você deve garantir que os lotes não tenham cruzamento de entervalos)
        var currentBatch = ticketBatchRepository.findActiveBatch();

        //A LÓGICA ABAIXO DEVE SER DELEGADA PARA OUTRO LUGAR, provavelmente para TicketBatchEntity.

        var batch = currentBatch.getTicketBatchType().stream()
                .map(tbt-> BatchTicketResponseDTO.builder()
                    .type(tbt.getTicketType())
                    .price(tbt.getPrice())
                    .totalQuantity(tbt.getTotalQuantity())
                    .availableQuantity(tbt.getTotalQuantity()-(tbt.getReservedQuantity()+tbt.getSoldQuantity()))
                    .build()
        ).toList();

        var pixFee = FeeDTO.builder()
                .type(pixFeeType)
                .value(pixFeeValue)
                .build();

        var creditCardFee = FeeDTO.builder()
                .type(creditCardFeeType)
                .value(creditCardFeeValue)
                .build();

        var pixMethod = PaymentMethodResponseDTO.builder()
                .type(PaymentMethodEnumEntity.PIX)
                .fee(pixFee)
                .build();

        var creditCardMethod = PaymentMethodResponseDTO.builder()
                .type(PaymentMethodEnumEntity.CREDIT_CARD)
                .fee(creditCardFee)
                .build();

        var purchaseConfig = PurchaseConfigResponseDTO.builder()
                .maxTicketsPerUser(ticketLimitPerUser)
                .paymentMethods(List.of(pixMethod,creditCardMethod))
                .build();

        return ActiveBatchInfoResponseDTO.builder()
                .batch(new BatchMetadataResponseDTO(currentBatch.getId(), currentBatch.getName()))
                .tickets(batch)
                .purchaseConfig(purchaseConfig)
                .build();

    }
}

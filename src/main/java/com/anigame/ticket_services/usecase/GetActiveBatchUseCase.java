package com.anigame.ticket_services.usecase;

import com.anigame.ticket_services.repository.ticket_batch.TicketBatchRepository;
import com.anigame.ticket_services.web.dto.response.ActiveBatchInfoResponseDTO;
import com.anigame.ticket_services.web.dto.response.BatchMetadataResponseDTO;
import com.anigame.ticket_services.web.dto.response.BatchTicketResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetActiveBatchUseCase {

    private final TicketBatchRepository ticketBatchRepository;

    public ActiveBatchInfoResponseDTO execute(){

        //a busca abaixo deverá retornar apenas um lote, com isso, vc deverá bolar uma estratégia para conseguir fazer isso (nesse caso atual você deve garantir que os lotes não tenham cruzamento de entervalos)
        var currentBatch = ticketBatchRepository.findActiveBatch();

        //A LÓGICA ABAIXO DEVE SER DELEGADA PARA OUTRO LUGAR, provavelmente para TicketBatchEntity.

        var batch = currentBatch.getTicketBatchType().stream().map(tbt->{
            return BatchTicketResponseDTO.builder()
                .type(tbt.getTicketType())
                .price(tbt.getPrice())
                .totalQuantity(tbt.getTotalQuantity())
                .reservedQuantity(tbt.getReservedQuantity())
                .soldQuantity(tbt.getSoldQuantity())
                .build();
                }
                )
                .toList();

        return ActiveBatchInfoResponseDTO.builder()
                .batch(new BatchMetadataResponseDTO(1))
                .tickets(batch)
                .build();

    }
}
